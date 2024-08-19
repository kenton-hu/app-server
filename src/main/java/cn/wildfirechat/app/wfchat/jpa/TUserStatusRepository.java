package cn.wildfirechat.app.wfchat.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TUserStatusRepository extends CrudRepository<TUserStatus, Integer> {

    /**
     * 根据用户id查询
     * @param uid 用户id
     * @return 用户状态
     */
    @Query("select ts.id, ts.uid, ts.status, ts.dt from TUserStatus ts where ts.uid = ?1")
    TUserStatus findByUid(String uid);
}
