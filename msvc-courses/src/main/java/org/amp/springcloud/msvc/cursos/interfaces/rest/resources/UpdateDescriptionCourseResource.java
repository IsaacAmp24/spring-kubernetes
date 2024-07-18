package org.amp.springcloud.msvc.cursos.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateDescriptionCourseResource(
        @NotBlank(message = "Description is required")
        String description
) {
}
