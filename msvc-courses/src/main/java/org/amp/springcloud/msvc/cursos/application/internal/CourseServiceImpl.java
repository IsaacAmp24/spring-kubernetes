package org.amp.springcloud.msvc.cursos.application.internal;

import org.amp.springcloud.msvc.cursos.domain.model.aggregates.Course;
import org.amp.springcloud.msvc.cursos.domain.services.CourseService;
import org.amp.springcloud.msvc.cursos.infrastructure.persistence.jpa.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    @Transactional(readOnly = true) // read-only transaction because we are only reading data
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true) // read-only transaction because we are only reading data
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }
}
