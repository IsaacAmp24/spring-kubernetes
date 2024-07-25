package org.amp.springcloud.msvc.cursos.application.internal.commandServiceImpl;

import org.amp.springcloud.msvc.cursos.domain.model.aggregates.Course;
import org.amp.springcloud.msvc.cursos.domain.model.commands.CreateCourseCommand;
import org.amp.springcloud.msvc.cursos.domain.model.commands.DeleteCourseCommand;
import org.amp.springcloud.msvc.cursos.domain.model.commands.UpdateDescriptionCourseCommand;
import org.amp.springcloud.msvc.cursos.domain.model.commands.UpdateNameCourseCommand;
import org.amp.springcloud.msvc.cursos.domain.services.CourseCommandService;
import org.amp.springcloud.msvc.cursos.infrastructure.persistence.jpa.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CourseCommandServiceImpl implements CourseCommandService {

    @Autowired
    private final CourseRepository courseRepository;

    public CourseCommandServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public Long handle(CreateCourseCommand command) {
        var courseName = command.name();
        // validar si exsite un curso con el mismo nombre
        if (courseRepository.existsByName(courseName)) {
            throw new IllegalArgumentException("Course with name " + courseName + " already exists");
        }

        var course = new Course(
                command.name(),
                command.description()
        );

        courseRepository.save(course);
        return course.getId();
    }

    @Override
    @Transactional
    public Optional<Course> handle(UpdateNameCourseCommand command) {
        var course = courseRepository.findById(command.id()).orElseThrow(() -> new IllegalArgumentException("Course with id " + command.id() + " does not exist"));

        // si el nombre es diferente de null, de "string" y no esta vacio, actualiza el nombre
        if (command.name() != null && !command.name().isBlank()) {
            course.setName(command.name());
        }

        return Optional.of(courseRepository.save(course));
    }

    @Override
    @Transactional
    public Optional<Course> handle(UpdateDescriptionCourseCommand command) {
        var course = courseRepository.findById(command.id()).orElseThrow(() -> new IllegalArgumentException("Course with id " + command.id() + " does not exist"));

        // si la descripcion es diferente de null, de "string" y no esta vacio, actualiza la descripcion
        if (command.description() != null && !command.description().isBlank()) {
            course.setDescription(command.description());
        }

        return Optional.of(courseRepository.save(course));
    }

    @Override
    @Transactional
    public void handle(DeleteCourseCommand command) {
        if (!courseRepository.existsById(command.courseId())) {
            throw new IllegalArgumentException("Course with id " + command.courseId() + " does not exist");
        }
        try {
            courseRepository.deleteById(command.courseId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting course with id " + command.courseId());
        }

    }
}
