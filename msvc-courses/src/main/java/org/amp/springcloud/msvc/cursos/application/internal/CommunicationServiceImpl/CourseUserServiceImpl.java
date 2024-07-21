package org.amp.springcloud.msvc.cursos.application.internal.CommunicationServiceImpl;

import org.amp.springcloud.msvc.cursos.domain.model.aggregates.Course;
import org.amp.springcloud.msvc.cursos.domain.model.entities.CourseUsers;
import org.amp.springcloud.msvc.cursos.domain.services.CourseUserService;
import org.amp.springcloud.msvc.cursos.infrastructure.clients.UserClientRest;
import org.amp.springcloud.msvc.cursos.infrastructure.persistence.jpa.repositories.CourseRepository;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    private final UserClientRest userClientRest;
    private final CourseRepository courseRepository;

    public CourseUserServiceImpl(UserClientRest userClientRest, CourseRepository courseRepository) {
        this.userClientRest = userClientRest;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public Optional<UserDTO> assignUserToCourse(UserDTO userId, Long courseId) {

        var course = courseRepository.findById(courseId);

        // si el curso existe y el usuario no es nulo entonces asignamos el usuario al curso
        if (course.isPresent() && userId != null) {
            UserDTO userDTO = userClientRest.getUserById(userId.id());
            Course course1 = course.get();
            CourseUsers courseUsers = new CourseUsers();
            courseUsers.setUserId(userDTO.id());

            course1.addCourseUser(courseUsers);
            courseRepository.save(course1);

            return Optional.of(userDTO);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserDTO> createUserByCourse(UserDTO userId, Long courseId) {
         var course = courseRepository.findById(courseId);

            if (course.isPresent() && userId != null) {
                UserDTO userDTO = userClientRest.createUser(userId);
                Course course1 = course.get();
                CourseUsers courseUsers = new CourseUsers();
                courseUsers.setUserId(userDTO.id());

                course1.addCourseUser(courseUsers);
                courseRepository.save(course1);

                return Optional.of(userDTO);
            }
            return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserDTO> unassignUserToCourse(UserDTO userId, Long courseId) {
        var course = courseRepository.findById(courseId);

        if (course.isPresent() && userId != null) {
            UserDTO userDTO = userClientRest.getUserById(userId.id());
            Course course1 = course.get();
            CourseUsers courseUsers = new CourseUsers();
            courseUsers.setUserId(userDTO.id());

            course1.removeCourseUser(courseUsers);
            courseRepository.save(course1);

            return Optional.of(userDTO);
        }
        return Optional.empty();
    }
}
