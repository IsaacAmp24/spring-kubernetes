package org.amp.springcloud.msvc.users.infrastructure.persistence.jpa.repositories;

import org.amp.springcloud.msvc.users.domain.model.aggregates.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

}
