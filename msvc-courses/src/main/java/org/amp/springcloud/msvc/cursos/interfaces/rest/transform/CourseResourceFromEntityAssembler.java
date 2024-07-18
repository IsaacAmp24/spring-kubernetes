package org.amp.springcloud.msvc.cursos.interfaces.rest.transform;

import org.amp.springcloud.msvc.cursos.domain.model.aggregates.Course;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.CourseResource;

public class CourseResourceFromEntityAssembler {
    public static CourseResource toResourceFromEntity(Course course) {
        return new CourseResource(
                course.getId(),
                course.getName(),
                course.getDescription()
        );
    }
}
