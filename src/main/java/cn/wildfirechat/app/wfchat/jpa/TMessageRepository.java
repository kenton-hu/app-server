package cn.wildfirechat.app.wfchat.jpa;

import cn.wildfirechat.app.admin.dto.resp.MessageRespDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TMessageRepository extends JpaRepository<TMessage, Integer>, JpaSpecificationExecutor<TMessage> {
    /**
     * 查询消息列表
     * @param tableName 表名
     * @param searchKey 搜索关键字
     * @param messageType 消息类型
     * @param type 会话类型
     * @param offset 偏移量
     * @param pageSize 分页大小
     * @return 消息列表
     */
    @Query(nativeQuery = true,
            value = "select * from (select id, _mid as mid, _from as `from`, _type as `type`, _target as target, _line as line, " +
                    "_data as data, _searchable_key as searchableKey, _dt as dt, _content_type as contentType," +
                    " _to as `to` from ?1 where if(?2 is not null, _searchable_key like concat('%',?2,'%'), 1=1) " +
                    "and if(?3 is not null, _content_type = ?3, 1=1) and if(?4 is not null, `type`=?4,1=1)) t " +
                    "left join t_user tu1 on tu1.`name` = t.`form` left join t_user tu2 on tu1.`name` = t.target  limit ?5, ？6")
    List<MessageRespDTO> queryMessage(String tableName, String searchKey, String messageType, String type, int offset, int pageSize);
}
