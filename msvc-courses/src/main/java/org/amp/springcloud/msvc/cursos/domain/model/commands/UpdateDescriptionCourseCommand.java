package org.amp.springcloud.msvc.cursos.domain.model.commands;

public record UpdateDescriptionCourseCommand(
        Long id,
        String description
) {
}
