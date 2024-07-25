package org.amp.springcloud.msvc.cursos.infrastructure.clients;

import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.CreateUserDTO;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-users", url = "http://localhost:8001")
public interface UserClientRest {

    // obtenenmos el usuario por id - [getUserById] tiene que ser el mismo en UserController
    @GetMapping("/api/users/{userId}")
    public UserDTO getUserById(@PathVariable Long userId);

    // creamos un usuario en base a un UserDTO - [createUser] tiene que ser el mismo en UsersController
    @PostMapping("/api/users")
    public UserDTO createUser(@RequestBody CreateUserDTO userDTO);

    // obtenemos lista de usuarios por su id
    @GetMapping("/api/users/all")
    List<UserDTO> getAllUsersByCourse(@RequestParam Iterable<Long> ids);


    // eliminamos un usuario por su id
    @DeleteMapping("/api/users/{userId}")
    void deleteUser(@PathVariable Long userId);


}
