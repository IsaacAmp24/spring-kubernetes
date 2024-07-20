package org.amp.springcloud.msvc.cursos.interfaces.rest.resources;

// record que representa el recurso de usuario
public record UserResource(
        Long id,
        String name,
        String email,
        String password
) {
}
