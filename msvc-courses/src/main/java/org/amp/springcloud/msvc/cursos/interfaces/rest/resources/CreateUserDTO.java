package org.amp.springcloud.msvc.cursos.interfaces.rest.resources;

public record CreateUserDTO(
        String name,
        String email,
        String password
) {
}
