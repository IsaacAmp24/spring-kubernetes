package org.amp.springcloud.msvc.cursos.domain.services;

import org.amp.springcloud.msvc.cursos.domain.model.aggregates.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    // queries
    List<Course> findAll();
    Optional<Course> findById(Long id);

    // commands
    Course save(Course course);
    void deleteById(Long id);
}
