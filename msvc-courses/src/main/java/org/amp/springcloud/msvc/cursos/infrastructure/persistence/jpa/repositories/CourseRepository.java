package org.amp.springcloud.msvc.cursos.infrastructure.persistence.jpa.repositories;

import org.amp.springcloud.msvc.cursos.domain.model.aggregates.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // no puede existir un curso con el mismo nombre
    boolean existsByName(String name);
}
