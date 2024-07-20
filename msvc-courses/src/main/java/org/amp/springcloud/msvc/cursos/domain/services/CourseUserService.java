package org.amp.springcloud.msvc.cursos.domain.services;

import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.UserDTO;

import java.util.Optional;

public interface CourseUserService {

    Optional<UserDTO> assignUserToCourse(UserDTO userId, Long courseId);
    Optional<UserDTO> createUserByCourse(UserDTO userId, Long courseId);
    Optional<UserDTO> unassignUserToCourse(UserDTO userId, Long courseId);

}
