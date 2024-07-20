package org.amp.springcloud.msvc.cursos.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.amp.springcloud.msvc.shared.domain.model.entities.AuditableModel;

@Entity
@Getter
@Setter
// tabla que se encarga de relacionar los cursos con los usuarios
public class CourseUsers extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true) //
    private Long userId;

    // este campo se encarga de relacionar el curso con el usuario
    @Override
    public boolean equals(Object obj) {
        // validamos si el objeto es el mismo
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CourseUsers)) {
            return false;
        }
        CourseUsers courseUsers = (CourseUsers) obj;
        return userId != null && userId.equals(courseUsers.userId); // comparamos si el usuario es el mismo
    }
}
