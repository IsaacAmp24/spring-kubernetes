package org.amp.springcloud.msvc.users.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true) // email is unique
    private String email;

    private String password;
}
