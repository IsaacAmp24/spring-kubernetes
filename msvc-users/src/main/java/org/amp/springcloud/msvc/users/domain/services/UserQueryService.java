package org.amp.springcloud.msvc.users.domain.services;

import org.amp.springcloud.msvc.users.domain.model.aggregates.Users;
import org.amp.springcloud.msvc.users.domain.model.queries.GetAllUsersQuery;
import org.amp.springcloud.msvc.users.domain.model.queries.GetUserByIdQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    // obtiene todos los usuarios
    List<Users> handle(GetAllUsersQuery query);

    // obtiene un usuario por su id
    Optional<Users> handle(GetUserByIdQuery query);

}
