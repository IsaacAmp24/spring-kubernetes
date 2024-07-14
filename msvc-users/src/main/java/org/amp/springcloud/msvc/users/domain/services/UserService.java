package org.amp.springcloud.msvc.users.domain.services;

import org.amp.springcloud.msvc.users.domain.model.aggregates.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // queries
    List<Users> findAll();
    Optional<Users> findById(Long id);

    // commands
    Users save(Users user);
    void deleteById(Long id);
}
