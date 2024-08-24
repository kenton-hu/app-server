package cn.wildfirechat.app.wfchat.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TUserRepository extends JpaRepository<TUser, Integer>, JpaSpecificationExecutor<TUser> {
    /**
     * 根据name查询
     * @param name 用户名称
     * @return
     */
    TUser findByName(String name);

    @Query("select u from TUser u where u.uid in :ids")
    List<TUser> findByUids(@Param("ids") List<String> ids);
}
