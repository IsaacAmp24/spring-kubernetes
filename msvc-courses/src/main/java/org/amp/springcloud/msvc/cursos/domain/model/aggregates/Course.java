package org.amp.springcloud.msvc.cursos.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.amp.springcloud.msvc.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
@Setter
public class Course extends AuditableAbstractAggregateRoot<Course> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The field name is required")
    private String name;

    @NotEmpty(message = "The field description is required")
    private String description;

    //private String users;

    public Course() {
    }

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
