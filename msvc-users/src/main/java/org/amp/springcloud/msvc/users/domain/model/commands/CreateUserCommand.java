package org.amp.springcloud.msvc.users.domain.model.commands;

public record CreateUserCommand(
        String name,
        String email,
        String password
) {
}
