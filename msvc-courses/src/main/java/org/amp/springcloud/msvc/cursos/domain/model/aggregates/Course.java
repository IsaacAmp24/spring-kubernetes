package org.amp.springcloud.msvc.cursos.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.amp.springcloud.msvc.cursos.domain.model.entities.CourseUsers;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.UserResource;
import org.amp.springcloud.msvc.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //
    @JoinColumn(name = "course_id") // nombre de la columna que se encarga de relacionar el curso con el usuario
    private List<CourseUsers> courseUsers; // un curso puede tener varios usuarios

    @Transient
    private List<UserResource> userResources;

    public Course() {
        courseUsers = new ArrayList<>(); // inicializamos la lista de cursosUsuarios
        userResources = new ArrayList<>(); // inicializamos la lista de usuarios
    }

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // método para agregar un usuario a un curso
    public void addCourseUser(CourseUsers courseUser) {
        courseUsers.add(courseUser);
    }

    // método para eliminar un usuario de un curso
    public void removeCourseUser(CourseUsers courseUser) {
        courseUsers.remove(courseUser);
    }


}
