package org.amp.springcloud.msvc.users.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// se hacen las validaciones antes de que los datos lleguen a la capa de negocio
public record CreateUserResource(
        @NotBlank(message = "Name cannot be null or empty.")
        String name,

        @Email(message = "Please provide a valid email address.")
        @NotBlank(message = "Email cannot be null or empty.")
        String email,

        @NotBlank(message = "Password cannot be null or empty.")
        String password
) {
}
