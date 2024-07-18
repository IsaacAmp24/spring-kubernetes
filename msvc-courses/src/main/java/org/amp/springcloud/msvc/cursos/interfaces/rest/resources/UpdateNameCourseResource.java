package org.amp.springcloud.msvc.cursos.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateNameCourseResource(
        @NotBlank(message = "Name is required")
        String name
) {
}
