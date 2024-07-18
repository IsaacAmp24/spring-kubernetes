package org.amp.springcloud.msvc.users.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateNameUserResource(
        @NotBlank(message = "Name is required")
        String name
) {
}
