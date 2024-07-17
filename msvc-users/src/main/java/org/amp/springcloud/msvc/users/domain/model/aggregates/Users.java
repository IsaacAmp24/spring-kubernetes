package org.amp.springcloud.msvc.users.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @Email(message = "Email is invalid")
    @Column(unique = true) // email is unique
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;
}