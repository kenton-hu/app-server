package cn.wildfirechat.app.wfchat.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TSensitiveMessageRepository extends JpaRepository<TSensitiveMessage, Integer> {

}
