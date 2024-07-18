package org.amp.springcloud.msvc.users.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.amp.springcloud.msvc.users.domain.model.valueobjects.EmailAddress;
import org.amp.springcloud.msvc.users.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Setter
@Getter
@Entity
public class Users extends AuditableAbstractAggregateRoot<Users> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @Embedded
    private EmailAddress email;

    @NotBlank
    private String password;

    public Users() {
    }

    public Users(String name, String email, String password) {
        this.name = name;
        this.email = new EmailAddress(email);
        this.password = password;
    }

    public String getEmail() {
        return email.email();
    }
}