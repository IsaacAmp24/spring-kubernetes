package org.amp.springcloud.msvc.users.domain.services;

import org.amp.springcloud.msvc.users.domain.model.aggregates.Users;
import org.amp.springcloud.msvc.users.domain.model.commands.CreateUserCommand;
import org.amp.springcloud.msvc.users.domain.model.commands.DeleteUserCommand;
import org.amp.springcloud.msvc.users.domain.model.commands.UpdateNameUserCommand;

import java.util.Optional;

public interface UserCommandService {
    Long handle(CreateUserCommand command);
    Optional<Users> handle (UpdateNameUserCommand command);
    void handle (DeleteUserCommand command);
}
