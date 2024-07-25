package org.amp.springcloud.msvc.users.application.internal.commandServiceImpl;

import org.amp.springcloud.msvc.users.domain.model.aggregates.Users;
import org.amp.springcloud.msvc.users.domain.model.commands.CreateUserCommand;
import org.amp.springcloud.msvc.users.domain.model.commands.DeleteUserCommand;
import org.amp.springcloud.msvc.users.domain.model.commands.UpdateNameUserCommand;
import org.amp.springcloud.msvc.users.domain.model.valueobjects.EmailAddress;
import org.amp.springcloud.msvc.users.domain.services.UserCommandService;
import org.amp.springcloud.msvc.users.infrastructure.clients.CourseClientRest;
import org.amp.springcloud.msvc.users.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class UserCommandServiceImpl implements UserCommandService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CourseClientRest courseClientRest;


    public UserCommandServiceImpl(UserRepository userRepository, CourseClientRest courseClientRest) {
        this.userRepository = userRepository;
        this.courseClientRest = courseClientRest;
    }

    @Override
    @Transactional
    public Long handle(CreateUserCommand command) {
        var emailAddress = new EmailAddress(command.email());

        // validar si ya existe un usuario con el mismo email
        if (userRepository.existsByEmail(emailAddress)) {
            throw new IllegalArgumentException("User with email " + command.email() + " already exists");
        }

        var user = new Users(
                command.name(),
                command.email(),
                command.password()
        );

        return userRepository.save(user).getId();
    }

    @Override
    @Transactional
    public Optional<Users> handle(UpdateNameUserCommand command) {
        Optional<Users> usersOptional = userRepository.findById(command.userId());

        // validar si el usuario al que se debe actualizar existe
        if (usersOptional.isEmpty()) {
            throw new IllegalArgumentException("User with id " + command.userId() + " does not exist");
        }

        Users user = usersOptional.get();
        user.setName(command.name());

        return Optional.of(userRepository.save(user));
    }

    @Override
    @Transactional
    public void handle(DeleteUserCommand command) {
        if (!userRepository.existsById(command.userId())) {
            throw new IllegalArgumentException("User with id " + command.userId() + " does not exist");
        }
        try {
            userRepository.deleteById(command.userId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting user with id " + command.userId());
        }
        // tambien tenemos que eliminar el usuario de los cursos (si esta asignado a alguno) - inyectamos el cliente de cursos
        courseClientRest.deleteUserFromCourse(command.userId());
    }
}
