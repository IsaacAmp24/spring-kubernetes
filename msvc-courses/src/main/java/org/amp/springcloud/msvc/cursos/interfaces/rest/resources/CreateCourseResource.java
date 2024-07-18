package org.amp.springcloud.msvc.cursos.interfaces.rest.resources;

public record CreateCourseResource(
        String name,
        String description
) {
}
