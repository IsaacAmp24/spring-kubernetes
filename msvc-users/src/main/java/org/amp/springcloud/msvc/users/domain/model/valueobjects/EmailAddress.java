package org.amp.springcloud.msvc.users.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

 // aqui validamos a nivel de valueObject -> que sera usado en la capa de servicio
@Embeddable
public record EmailAddress(String email) {

    public EmailAddress {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Please provide a valid email address.");
        } else if (email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");

        }
    }

    // valida si el email es valido
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
