package org.amp.springcloud.msvc.cursos.interfaces.rest.resources;

import org.amp.springcloud.msvc.cursos.domain.model.entities.CourseUsers;

import java.util.List;

public record CourseResource(
        Long id,
        String name,
        String description,
        List<UserDTO> users,
        List<CourseUsers> courseUsers
) {
}
