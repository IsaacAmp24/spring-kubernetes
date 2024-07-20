package org.amp.springcloud.msvc.cursos.application.internal.CommunicationServiceImpl;

import org.amp.springcloud.msvc.cursos.domain.services.CourseUserService;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.UserDTO;

import java.util.Optional;

public class CourseUserServiceImpl implements CourseUserService {
    @Override
    public Optional<UserDTO> assignUserToCourse(UserDTO userId, Long courseId) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> createUserByCourse(UserDTO userId, Long courseId) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> unassignUserToCourse(UserDTO userId, Long courseId) {
        return Optional.empty();
    }
}
