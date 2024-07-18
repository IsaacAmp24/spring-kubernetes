package org.amp.springcloud.msvc.cursos.domain.model.commands;

public record CreateCourseCommand(
        String name,
        String description
) {
}