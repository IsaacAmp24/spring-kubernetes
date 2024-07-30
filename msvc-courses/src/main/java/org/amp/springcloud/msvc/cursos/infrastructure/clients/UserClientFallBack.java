package org.amp.springcloud.msvc.cursos.infrastructure.clients;

import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.CreateUserDTO;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserClientFallBack implements UserClientRest {

    @Override
    public UserDTO getUserById(Long userId) {
        return null;
    }

    @Override
    public UserDTO createUser(CreateUserDTO userDTO) {
        return null;
    }

    @Override
    public List<UserDTO> getAllUsersByCourse(Iterable<Long> ids) {
        return List.of();
    }

    @Override
    public void deleteUser(Long userId) {
        // Lógica de fallback, por ejemplo, loguear y retornar un mensaje de error amigable
        System.out.println("Fallback - No se pudo eliminar el usuario");
    }
}
