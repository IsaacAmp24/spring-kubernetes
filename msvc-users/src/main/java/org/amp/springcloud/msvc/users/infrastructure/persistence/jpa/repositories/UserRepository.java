package org.amp.springcloud.msvc.users.infrastructure.persistence.jpa.repositories;

import org.amp.springcloud.msvc.users.domain.model.aggregates.Users;
import org.amp.springcloud.msvc.users.domain.model.valueobjects.EmailAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(EmailAddress email);
}
