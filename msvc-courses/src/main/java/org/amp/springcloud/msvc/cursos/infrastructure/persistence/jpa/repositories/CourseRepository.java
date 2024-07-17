package org.amp.springcloud.msvc.cursos.infrastructure.persistence.jpa.repositories;

import org.amp.springcloud.msvc.cursos.domain.model.aggregates.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

}
