package org.amp.springcloud.msvc.cursos.domain.services;

import org.amp.springcloud.msvc.cursos.domain.model.aggregates.Course;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.CreateUserDTO;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.UserDTO;

import java.util.Optional;

public interface CourseUserService {

    Optional<UserDTO> assignUserToCourse(UserDTO userId, Long courseId);
    Optional<CreateUserDTO> createUserByCourse(CreateUserDTO userId, Long courseId);
    Optional<UserDTO> unassignUserToCourse(UserDTO userId, Long courseId);
    Optional<Course> getAllUsersByCourse(Long courseId);

    // eliminar un usuario de todos los cursos
    void deleteUserFromAllCourses(Long userId);
}