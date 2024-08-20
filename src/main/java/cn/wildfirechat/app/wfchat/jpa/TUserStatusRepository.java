package cn.wildfirechat.app.wfchat.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TUserStatusRepository extends CrudRepository<TUserStatus, Integer> {

    /**
     * 根据用户id查询
     * @param uid 用户id
     * @return 用户状态
     */
    @Query("select ts.id, ts.uid, ts.status, ts.dt from TUserStatus ts where ts.uid = ?1")
    TUserStatus findByUid(String uid);

    /**
     * 根据用户状态查询
     * @param userStatus 用户状态
     * @return 用户状态列表
     */
    @Query("select ts.id, ts.uid, ts.status, ts.dt from TUserStatus ts where ts.status = ?1")
    List<TUserStatus> findByStatus(int userStatus);
}
