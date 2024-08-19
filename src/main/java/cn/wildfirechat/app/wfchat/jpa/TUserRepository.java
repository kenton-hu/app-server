package cn.wildfirechat.app.wfchat.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TUserRepository extends CrudRepository<TUser, Integer> {
}
