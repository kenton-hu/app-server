package cn.wildfirechat.app.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wildfirechat.app.admin.dto.req.MessageListReqDTO;
import cn.wildfirechat.app.admin.dto.req.RecallMessageReqDTO;
import cn.wildfirechat.app.admin.dto.resp.MessageRespDTO;
import cn.wildfirechat.app.admin.dto.resp.PageRespDTO;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.service.TMessageService;
import cn.wildfirechat.app.admin.utils.MessageUtils;
import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.sdk.MessageAdmin;
import cn.wildfirechat.sdk.model.IMResult;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class TMessageServiceImpl implements TMessageService {
    private static final Logger LOG = LoggerFactory.getLogger(TMessageServiceImpl.class);
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result<?> recall(RecallMessageReqDTO reqDTO) {
        LOG.info("recall: {}", reqDTO);
        IMResult<String> recallIMResult = new IMResult<>();
        try {
            recallIMResult = MessageAdmin.recallMessage("admin", reqDTO.getMessageId());
            if (recallIMResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return new Result<>().success(recallIMResult.getResult(), reqDTO.getSessionId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Result<>().error(String.valueOf(recallIMResult.getCode()), recallIMResult.getMsg(), reqDTO.getSessionId());
    }

    @Override
    public Result<?> messageList(MessageListReqDTO reqDTO) {
        long total = getMessageListTotal(reqDTO);
        PageRespDTO<MessageRespDTO> pageRespDTO = new PageRespDTO<>(reqDTO.getPageSize(), reqDTO.getPageNo());
        if (total == 0) {
            pageRespDTO.setTotalCount(0);
            pageRespDTO.setTotalPage(0);
        } else {
            List<MessageRespDTO> tMessages = getMessageList(reqDTO);
            pageRespDTO.setTotalCount(total);
            pageRespDTO.setTotalPage((int) Math.ceil((double) total / reqDTO.getPageSize()));
            pageRespDTO.setItems(tMessages);
        }
        return new Result<>().success(pageRespDTO, reqDTO.getSessionId());
    }

    /**
     * 获取消息列表
     * @param reqDTO 请求参数
     * @return
     */
    private List<MessageRespDTO> getMessageList(MessageListReqDTO reqDTO) {
        List<MessageRespDTO> result = new ArrayList<>();
        StringBuilder messageBuilder = new StringBuilder("select id, _mid as mid, _from as `from`, _type as `type`, _target as target, _line as line, " +
                " _data as data, _searchable_key as searchableKey, _dt as dt, _content_type as contentType," +
                " _to as `to` from wfchat." + MessageUtils.genTableName("t_messages", reqDTO.getMonthNo()));

        StringBuilder whereBuilder = new StringBuilder(" where 1 = 1 ");
        if (StrUtil.isNotBlank(reqDTO.getSearchKey())) {
            whereBuilder.append(" and _searchable_key like concat('%', :searchableKey, '%')");
        }
        if (StrUtil.isNotBlank(reqDTO.getMessageType())) {
            whereBuilder.append(" and _content_type = :contentType");
        }
        if (StrUtil.isNotBlank(reqDTO.getType())) {
            whereBuilder.append(" and _type = :type");
        }
        StringBuilder sqlBuilder = new StringBuilder("select t.*, tu1._name as fromName, tu2._name as targetName from (");
        sqlBuilder.append(messageBuilder).append(whereBuilder).append(") t ");
        StringBuilder joinBuilder = new StringBuilder();
        joinBuilder.append(" left join wfchat.t_user tu1 on tu1.`_name` = t.`from` left join wfchat.t_user tu2 on tu2.`_name` = t.target order by t.dt desc");
        sqlBuilder.append(joinBuilder);
        // 分页
        StringBuilder limitBuilder = new StringBuilder( " limit " + (reqDTO.getPageSize() * (reqDTO.getPageNo() - 1)) + "," + reqDTO.getPageSize());
        sqlBuilder.append(limitBuilder);

        Query nativeQuery = entityManager.createNativeQuery(sqlBuilder.toString());
        nativeQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        if (StrUtil.isNotBlank(reqDTO.getSearchKey())) {
            nativeQuery.setParameter("searchableKey", reqDTO.getSearchKey());
        }
        if (StrUtil.isNotBlank(reqDTO.getMessageType())) {
            nativeQuery.setParameter("contentType", reqDTO.getMessageType());
        }
        if (StrUtil.isNotBlank(reqDTO.getType())) {
            nativeQuery.setParameter("type", reqDTO.getType());
        }

        List resultList = nativeQuery.getResultList();
        if (CollUtil.isNotEmpty(resultList)) {
            result = BeanUtil.copyToList(resultList, MessageRespDTO.class);
        }
        return result;

    }

    /**
     * 获取消息列表总数
     * @param reqDTO 请求参数
     * @return
     */
    private long getMessageListTotal(MessageListReqDTO reqDTO) {
        StringBuilder totalSelectBuilder = new StringBuilder("select count(1) from wfchat." + MessageUtils.genTableName("t_messages", reqDTO.getMonthNo()));
        StringBuilder totalWhereBuilder = new StringBuilder(" where 1 = 1 ");
        if (StrUtil.isNotBlank(reqDTO.getSearchKey())) {
            totalWhereBuilder.append(" and _searchable_key like concat('%', :searchableKey, '%')");
        }
        if (StrUtil.isNotBlank(reqDTO.getMessageType())) {
            totalWhereBuilder.append(" and _content_type = :contentType");
        }
        if (StrUtil.isNotBlank(reqDTO.getType())) {
            totalWhereBuilder.append(" and _type = :type");
        }
        StringBuilder totalSqlBuilder = new StringBuilder();
        totalSqlBuilder.append(totalSelectBuilder).append(totalWhereBuilder);

        // 获取消息总数
        Query totalQuery = entityManager.createNativeQuery(totalSqlBuilder.toString());
        totalQuery.unwrap(NativeQueryImpl.class);
        if (StrUtil.isNotBlank(reqDTO.getSearchKey())) {
            totalQuery.setParameter("searchableKey", reqDTO.getSearchKey());
        }
        if (StrUtil.isNotBlank(reqDTO.getMessageType())) {
            totalQuery.setParameter("contentType", reqDTO.getMessageType());
        }
        if (StrUtil.isNotBlank(reqDTO.getType())) {
            totalQuery.setParameter("type", reqDTO.getType());
        }
        return ((BigInteger)totalQuery.getSingleResult()).longValue();
    }
}
