package org.amp.springcloud.msvc.cursos.domain.services;

import org.amp.springcloud.msvc.cursos.domain.model.aggregates.Course;
import org.amp.springcloud.msvc.cursos.domain.model.queries.GetAllCoursesQuery;
import org.amp.springcloud.msvc.cursos.domain.model.queries.GetCourseByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CourseQueryService {
    List<Course> handle(GetAllCoursesQuery query);
    Optional<Course> handle(GetCourseByIdQuery query);
}
