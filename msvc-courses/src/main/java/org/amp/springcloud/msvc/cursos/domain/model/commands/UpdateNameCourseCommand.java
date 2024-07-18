package org.amp.springcloud.msvc.cursos.domain.model.commands;

public record UpdateNameCourseCommand(
        Long id,
        String name
) {
}
