package org.amp.springcloud.msvc.users.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.amp.springcloud.msvc.users.domain.model.aggregates.Users;
import org.amp.springcloud.msvc.users.domain.model.commands.UpdateNameUserCommand;
import org.amp.springcloud.msvc.users.domain.model.queries.GetAllUsersByIdQuery;
import org.amp.springcloud.msvc.users.domain.model.queries.GetAllUsersQuery;
import org.amp.springcloud.msvc.users.domain.model.queries.GetUserByIdQuery;
import org.amp.springcloud.msvc.users.domain.services.UserCommandService;
import org.amp.springcloud.msvc.users.domain.services.UserQueryService;
import org.amp.springcloud.msvc.users.infrastructure.clients.CourseClientRest;
import org.amp.springcloud.msvc.users.interfaces.rest.resources.CreateUserResource;
import org.amp.springcloud.msvc.users.interfaces.rest.resources.UpdateNameUserResource;
import org.amp.springcloud.msvc.users.interfaces.rest.resources.UserResource;
import org.amp.springcloud.msvc.users.interfaces.rest.transform.CreateUserResourceCommandFromResourceAssembler;
import org.amp.springcloud.msvc.users.interfaces.rest.transform.UserResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Users", description = "Users API")
@RequestMapping(value = "/api/users", produces = APPLICATION_JSON_VALUE)
public class UsersController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final CourseClientRest courseClientRest;

    
    public UsersController(UserCommandService userCommandService, UserQueryService userQueryService, CourseClientRest courseClientRest) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
        this.courseClientRest = courseClientRest;
    }

    @PostMapping
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody CreateUserResource createUserResource) {
        var createUserCommand = CreateUserResourceCommandFromResourceAssembler.toCommandFromResource(createUserResource);
        var userId = userCommandService.handle(createUserCommand);

        return userQueryService.handle(new GetUserByIdQuery(userId))
                .map(userEntity -> new ResponseEntity<>
                        (UserResourceFromEntityAssembler.toResourceFromEntity(userEntity),
                                HttpStatus.CREATED))

                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var getAllUsersQuery = new GetAllUsersQuery();
        var users = userQueryService.handle(getAllUsersQuery);

        var userResources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return new ResponseEntity<>(userResources, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);

        return user.map(userEntity -> ResponseEntity.ok(
                        UserResourceFromEntityAssembler.toResourceFromEntity(userEntity)))
                .orElse(ResponseEntity.notFound()
                        .header("message", "User with ID " + userId + " not found")
                        .build());
    }


    @PatchMapping("/{userId}/name")
    public ResponseEntity<UserResource> updateNameUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateNameUserResource updateNameUserResource) {

        var updateNameUserCommand = new UpdateNameUserCommand(userId, updateNameUserResource.name());
        Optional<Users> updatedUser = userCommandService.handle(updateNameUserCommand);

        if (updatedUser.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        UserResource userResource = UserResourceFromEntityAssembler.toResourceFromEntity(updatedUser.get());
        return ResponseEntity.ok(userResource);
    }

    // obtenemos todos los usuarios por su id (lo usaremos en el servicio cursos para obtener cuantos usuarios hay en un curso)
    @GetMapping("/all")
    public ResponseEntity<List<UserResource>> getAllUsersById(@RequestParam List<Long> ids) {
        var getAllUsersByIdQuery = new GetAllUsersByIdQuery(ids);
        var users = userQueryService.handle(getAllUsersByIdQuery);

        var userResources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return new ResponseEntity<>(userResources, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        courseClientRest.unassignUserFromCourses(userId);
        userCommandService.deleteUsers(userId);
        return ResponseEntity.noContent().build();
    }

}