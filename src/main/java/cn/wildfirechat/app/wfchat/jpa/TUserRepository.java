package cn.wildfirechat.app.wfchat.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TUserRepository extends JpaRepository<TUser, Integer>, JpaSpecificationExecutor<TUser> {
}
