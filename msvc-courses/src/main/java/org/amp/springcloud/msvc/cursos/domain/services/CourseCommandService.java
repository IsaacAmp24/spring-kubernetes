package org.amp.springcloud.msvc.cursos.domain.services;

import org.amp.springcloud.msvc.cursos.domain.model.aggregates.Course;
import org.amp.springcloud.msvc.cursos.domain.model.commands.CreateCourseCommand;
import org.amp.springcloud.msvc.cursos.domain.model.commands.DeleteCourseCommand;
import org.amp.springcloud.msvc.cursos.domain.model.commands.UpdateDescriptionCourseCommand;
import org.amp.springcloud.msvc.cursos.domain.model.commands.UpdateNameCourseCommand;

import java.util.Optional;


public interface CourseCommandService {
    Long handle (CreateCourseCommand command);
    Optional<Course> handle (UpdateNameCourseCommand command);
    Optional<Course> handle (UpdateDescriptionCourseCommand command);
    void handle (DeleteCourseCommand command);
}