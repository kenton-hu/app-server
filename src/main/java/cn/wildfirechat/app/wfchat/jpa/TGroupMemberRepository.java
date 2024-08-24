package cn.wildfirechat.app.wfchat.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TGroupMemberRepository extends JpaRepository<TGroupMember, Integer>, JpaSpecificationExecutor<TGroupMember> {

    @Query("select gm from TGroupMember gm where gm.gid in :gid")
    List<TGroupMember> findByGid(@Param("gid") String gid);
}
