package org.amp.springcloud.msvc.users.application.internal.queryServiceImpl;

import org.amp.springcloud.msvc.users.domain.model.aggregates.Users;
import org.amp.springcloud.msvc.users.domain.model.queries.GetAllUsersQuery;
import org.amp.springcloud.msvc.users.domain.model.queries.GetUserByIdQuery;
import org.amp.springcloud.msvc.users.domain.services.UserQueryService;
import org.amp.springcloud.msvc.users.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserQueryServiceImpl implements UserQueryService{

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Users> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.id());
    }
}
