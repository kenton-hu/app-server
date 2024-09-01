package cn.wildfirechat.app.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.wildfirechat.app.admin.dto.req.IndexInfoReqDTO;
import cn.wildfirechat.app.admin.dto.resp.IndexInfoRespDTO;
import cn.wildfirechat.app.admin.dto.resp.IndexTop10RespDTO;
import cn.wildfirechat.app.admin.service.IndexService;
import cn.wildfirechat.app.admin.utils.DateUtils;
import cn.wildfirechat.app.admin.utils.MessageUtils;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    private static final Logger LOG = LoggerFactory.getLogger(IndexServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<IndexInfoRespDTO> dailyNewUserCount(IndexInfoReqDTO reqDTO) {
        String startDate = getStartDate(reqDTO);
        String endDate = getEndDate(reqDTO);
        List<IndexInfoRespDTO> result = initDateRangeList(endDate);
        // 组装sql
//        String sql = "WITH RECURSIVE date_series AS (  \n" +
//                "  SELECT '" + startDate + "' AS `date`  \n" +
//                "  UNION ALL  \n" +
//                "  SELECT DATE_ADD(date, INTERVAL 1 DAY)  \n" +
//                "  FROM date_series  \n" +
//                "  WHERE date < '" + endDate + "'  \n" +
//                ")  \n" +
//                "select ds.date, count(tu.id) as `count` from date_series ds left join wfchat.t_user tu on DATE_FORMAT(tu._createTime,'%Y-%m-%d') = ds.date group by ds.date order by ds.date";
        String sql = "select DATE_FORMAT(tu._createTime,'%Y-%m-%d') as `date`, count(tu.id) as `count` from wfchat.t_user tu where DATE_FORMAT(tu._createTime,'%Y-%m-%d') between '" + startDate + "' and '" + endDate + "' group by `date`";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List resultList = query.getResultList();
        if (CollUtil.isNotEmpty(resultList)) {
            List<IndexInfoRespDTO> list = BeanUtil.copyToList(resultList, IndexInfoRespDTO.class);
            Map<String, String> map = list.stream().collect(Collectors.toMap(IndexInfoRespDTO::getDate, IndexInfoRespDTO::getCount));
            for (IndexInfoRespDTO respDTO : result) {
                if (map.containsKey(respDTO.getDate())) {
                    respDTO.setCount(map.get(respDTO.getDate()));
                }
            }
        }
        return result;
    }

    /**
     * 初始化日期范围列表
     * @return
     */
    private List<IndexInfoRespDTO> initDateRangeList(String endDate) {
        List<IndexInfoRespDTO> result = new ArrayList<>();
        // 日期数据
        Map<String, Long> rangeDate = DateUtils.getRangeDate(endDate, 6, DatePattern.NORM_DATE_PATTERN);
        if (CollUtil.isNotEmpty(rangeDate)) {
            for (Map.Entry<String, Long> entry : rangeDate.entrySet()) {
                result.add(new IndexInfoRespDTO(String.valueOf(entry.getValue()), entry.getKey()));
            }
        }
        return result;
    }

    @Override
    public List<IndexInfoRespDTO> dailyActiveUserCount(IndexInfoReqDTO reqDTO) {
        String startDate = getStartDate(reqDTO);
        String endDate = getEndDate(reqDTO);
        List<IndexInfoRespDTO> result = initDateRangeList(endDate);
        // 组装sql
//        String sql = "WITH RECURSIVE date_series AS (  \n" +
//                "  SELECT '" + startDate + "' AS `date`  \n" +
//                "  UNION ALL  \n" +
//                "  SELECT DATE_ADD(date, INTERVAL 1 DAY)  \n" +
//                "  FROM date_series  \n" +
//                "  WHERE date < '" + endDate + "'  \n" +
//                ")  \n" +
//                "select ds.date, count(tus.id) as `count` from date_series ds left join wfchat.t_user_session tus on from_unixtime(_dt / 1000, '%Y-%m-%d') = ds.date group by ds.date order by ds.date";
        String sql = "select from_unixtime(_dt / 1000, '%Y-%m-%d') as `date`, count(tus.id) as `count` from wfchat.t_user_session tus WHERE from_unixtime(_dt / 1000, '%Y-%m-%d') between '" + startDate + "' and '" + endDate+ "' group by `date` order by `date`";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List resultList = query.getResultList();
        if (CollUtil.isNotEmpty(resultList)) {
            List<IndexInfoRespDTO> list = BeanUtil.copyToList(resultList, IndexInfoRespDTO.class);
            Map<String, String> map = list.stream().collect(Collectors.toMap(IndexInfoRespDTO::getDate, IndexInfoRespDTO::getCount));
            for (IndexInfoRespDTO respDTO : result) {
                if (map.containsKey(respDTO.getDate())) {
                    respDTO.setCount(map.get(respDTO.getDate()));
                }
            }
        }
        return result;
    }

    @Override
    public List<IndexInfoRespDTO> totalUser(IndexInfoReqDTO reqDTO) {
        List<IndexInfoRespDTO> result = new ArrayList<>();
        // 组装sql
        String sql = "WITH DailyCounts AS (  \n" +
                "    SELECT  \n" +
                "        DATE_FORMAT(_createTime,'%Y-%m-%d') as `date` ,  \n" +
                "        COUNT(*) AS daily_count  \n" +
                "    FROM  \n" +
                "        wfchat.t_user \n" +
                "    GROUP BY  \n" +
                "        `date` \n" +
                "    ORDER BY  \n" +
                "         `date` \n" +
                ")  \n" +
                "SELECT  \n" +
                "    `date`,  \n" +
                "    daily_count,  \n" +
                "    SUM(daily_count) OVER (ORDER BY  `date` ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS `count`  \n" +
                "FROM  \n" +
                "    DailyCounts;";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List resultList = query.getResultList();
        if (CollUtil.isNotEmpty(resultList)) {
            result = BeanUtil.copyToList(resultList, IndexInfoRespDTO.class);
        }
        return result;
    }

    @Override
    public List<IndexInfoRespDTO> dailySendMsgCount(IndexInfoReqDTO reqDTO) {
        String startDate = getStartDate(reqDTO);
        String endDate = getEndDate(reqDTO);
        List<IndexInfoRespDTO> result = initDateRangeList(endDate);
        String monthNo = DateUtils.getDateByFormat(reqDTO.getDate(), DatePattern.SIMPLE_MONTH_PATTERN);
        // 组装sql
//        String sql = "WITH RECURSIVE date_series AS (  \n" +
//                "  SELECT '" + startDate + "' AS `date`  \n" +
//                "  UNION ALL  \n" +
//                "  SELECT DATE_ADD(date, INTERVAL 1 DAY)  \n" +
//                "  FROM date_series  \n" +
//                "  WHERE date < '" + endDate + "'  \n" +
//                ")  \n" +
//                "select ds.date, count(tm.id) as `count` from date_series ds left join wfchat." + MessageUtils.genTableName("t_messages", monthNo) + " tm on DATE_FORMAT(tm._dt,'%Y-%m-%d') = ds.date group by ds.date order by ds.date";
        String sql = "select DATE_FORMAT(tm._dt,'%Y-%m-%d') as `date`, count(tm.id) as `count` from wfchat." + MessageUtils.genTableName("t_messages", monthNo) + " tm WHERE DATE_FORMAT(tm._dt,'%Y-%m-%d') between '" + startDate + "' and '" + endDate + "' group by `date` order by `date`";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List resultList = query.getResultList();
        if (CollUtil.isNotEmpty(resultList)) {
            List<IndexInfoRespDTO> list = BeanUtil.copyToList(resultList, IndexInfoRespDTO.class);
            Map<String, String> map = list.stream().collect(Collectors.toMap(IndexInfoRespDTO::getDate, IndexInfoRespDTO::getCount));
            for (IndexInfoRespDTO respDTO : result) {
                if (map.containsKey(respDTO.getDate())) {
                    respDTO.setCount(map.get(respDTO.getDate()));
                }
            }
        }
        return result;
    }

    @Override
    public  List<IndexInfoRespDTO> dailyCreateGroup(IndexInfoReqDTO reqDTO) {
        String startDate = getStartDate(reqDTO);
        String endDate = getEndDate(reqDTO);
        List<IndexInfoRespDTO> result = initDateRangeList(endDate);
        // 组装sql
//        String sql = "WITH RECURSIVE date_series AS (  \n" +
//                "  SELECT '" + startDate + "' AS `date`  \n" +
//                "  UNION ALL  \n" +
//                "  SELECT DATE_ADD(date, INTERVAL 1 DAY)  \n" +
//                "  FROM date_series  \n" +
//                "  WHERE date < '" + endDate + "'  \n" +
//                ")  \n" +
//                "select ds.date, count(tg.id) as `count` from date_series ds left join wfchat.t_group tg on DATE_FORMAT(tg._dt,'%Y-%m-%d') = ds.date group by ds.date";
        String sql = "select DATE_FORMAT(tg._dt,'%Y-%m-%d') as `date`, count(tg.id) as `count` from wfchat.t_group tg WHERE DATE_FORMAT(tg._dt,'%Y-%m-%d') between '" + startDate + "' and '" + endDate + "' group by `date` order by `date`";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List resultList = query.getResultList();
        if (CollUtil.isNotEmpty(resultList)) {;
            List<IndexInfoRespDTO> list = BeanUtil.copyToList(resultList, IndexInfoRespDTO.class);
            Map<String, String> map = list.stream().collect(Collectors.toMap(IndexInfoRespDTO::getDate, IndexInfoRespDTO::getCount));
            for (IndexInfoRespDTO respDTO : result) {
                if (map.containsKey(respDTO.getDate())) {
                    respDTO.setCount(map.get(respDTO.getDate()));
                }
            }
        }
        return result;
    }

    @Override
    public List<IndexInfoRespDTO> totalCreateGroup(IndexInfoReqDTO reqDTO) {
        List<IndexInfoRespDTO> result = new ArrayList<>();
        // 组装sql
        String sql = "WITH DailyCounts AS (  \n" +
                "    SELECT  \n" +
                "        DATE_FORMAT(_createTime,'%Y-%m-%d') as `date` ,  \n" +
                "        COUNT(*) AS daily_count  \n" +
                "    FROM  \n" +
                "        wfchat.t_group  \n" +
                "    GROUP BY  \n" +
                "        `date` \n" +
                "    ORDER BY  \n" +
                "        `date`\n" +
                ")  \n" +
                "SELECT  \n" +
                "    `date`,  \n" +
                "    daily_count,  \n" +
                "    SUM(daily_count) OVER (ORDER BY `date` ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS `count`  \n" +
                "FROM  \n" +
                "    DailyCounts;";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List resultList = query.getResultList();
        if (CollUtil.isNotEmpty(resultList)) {
            result = BeanUtil.copyToList(resultList, IndexInfoRespDTO.class);
        }
        return result;
    }

    @Override
    public List<IndexInfoRespDTO> top100Image(IndexInfoReqDTO reqDTO) {
        return Collections.emptyList();
    }

    @Override
    public List<IndexTop10RespDTO> top10ActiveUser(IndexInfoReqDTO reqDTO) {
        List<IndexTop10RespDTO> result = new ArrayList<>();
        String endDate = getEndDate(reqDTO);
        String monthNo = DateUtils.getDateByFormat(reqDTO.getDate(), DatePattern.SIMPLE_MONTH_PATTERN);

        // 组装sql
        String sql = "select t._from as name, tu._display_name as display_name, t.`total` from (\n" +
                "\tselect _from, count(1) as `total` from wfchat." + MessageUtils.genTableName("t_messages", monthNo) + " where DATE_FORMAT(_dt, '%Y-%m-%d') = '" + endDate + "' group by `_from`\n" +
                ") t\n" +
                "left join wfchat.t_user tu on t._from = tu._uid";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List resultList = query.getResultList();
        if (CollUtil.isNotEmpty(resultList)) {
            result = BeanUtil.copyToList(resultList, IndexTop10RespDTO.class);
        }
        return result;
    }

    @Override
    public List<IndexTop10RespDTO> top10ActiveGroup(IndexInfoReqDTO reqDTO) {
        List<IndexTop10RespDTO> result = new ArrayList<>();
        String endDate = getEndDate(reqDTO);
        String monthNo = DateUtils.getDateByFormat(reqDTO.getDate(), DatePattern.SIMPLE_MONTH_PATTERN);
        // 组装sql
        String sql = "select t._target as name, tg._name as display_name, t.`total` from (\n" +
                "\tselect _target, count(1) as `total` from wfchat." + MessageUtils.genTableName("t_messages", monthNo) + " where DATE_FORMAT(_dt, '%Y-%m-%d') = '" + endDate + "' and _type = 1 group by `_target`\n" +
                ") t\n" +
                "join wfchat.t_group tg on t._target = tg._gid order by t.`total` desc\n";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List resultList = query.getResultList();
        if (CollUtil.isNotEmpty(resultList)) {
            result = BeanUtil.copyToList(resultList, IndexTop10RespDTO.class);
        }
        return result;
    }

    /**
     * 获取开始日期
     * @param reqDTO
     * @return
     */
    private String getStartDate(IndexInfoReqDTO reqDTO) {
        String date = reqDTO.getDate();
        if (StrUtil.isBlank(date)) {
            date = DateUtil.format(new DateTime(), DatePattern.NORM_DATE_PATTERN);
        }
        return DateUtils.getDateOffsetByFormat(date, -6, DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 获取结束日期
     * @param reqDTO
     * @return
     */
    private String getEndDate(IndexInfoReqDTO reqDTO) {
        String date = reqDTO.getDate();
        if (StrUtil.isBlank(date)) {
            date = DateUtil.format(new DateTime(), DatePattern.NORM_DATE_PATTERN);
        }
        return DateUtils.getDateByFormat(date, DatePattern.NORM_DATE_PATTERN);
    }
}
