package org.amp.springcloud.msvc.users.domain.model.commands;

public record UpdateNameUserCommand(
        Long userId,
        String name
) {
}