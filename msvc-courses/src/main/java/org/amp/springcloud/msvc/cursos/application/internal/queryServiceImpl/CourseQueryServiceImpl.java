package org.amp.springcloud.msvc.cursos.application.internal.queryServiceImpl;

import org.amp.springcloud.msvc.cursos.domain.model.aggregates.Course;
import org.amp.springcloud.msvc.cursos.domain.model.queries.GetAllCoursesQuery;
import org.amp.springcloud.msvc.cursos.domain.model.queries.GetCourseByIdQuery;
import org.amp.springcloud.msvc.cursos.domain.services.CourseQueryService;
import org.amp.springcloud.msvc.cursos.infrastructure.persistence.jpa.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseQueryServiceImpl implements CourseQueryService {

    @Autowired
    private final CourseRepository courseRepository;

    public CourseQueryServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @Override
    public List<Course> handle(GetAllCoursesQuery query) {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> handle(GetCourseByIdQuery query) {
        return courseRepository.findById(query.id());
    }
}
