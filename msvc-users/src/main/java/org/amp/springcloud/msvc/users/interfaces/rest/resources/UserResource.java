package org.amp.springcloud.msvc.users.interfaces.rest.resources;

public record UserResource(
        Long id,
        String name,
        String email,
        String password
) {
}
