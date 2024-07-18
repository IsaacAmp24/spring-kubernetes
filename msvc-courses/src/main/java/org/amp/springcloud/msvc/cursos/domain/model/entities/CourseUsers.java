package org.amp.springcloud.msvc.cursos.domain.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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


    private Long userId;

}
