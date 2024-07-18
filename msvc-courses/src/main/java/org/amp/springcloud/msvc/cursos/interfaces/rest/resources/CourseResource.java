package org.amp.springcloud.msvc.cursos.interfaces.rest.resources;

public record CourseResource(
        Long id,
        String name,
        String description
) {
}
