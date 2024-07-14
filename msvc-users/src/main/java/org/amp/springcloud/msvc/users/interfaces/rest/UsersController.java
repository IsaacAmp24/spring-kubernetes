package org.amp.springcloud.msvc.users.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.amp.springcloud.msvc.users.domain.model.aggregates.Users;
import org.amp.springcloud.msvc.users.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Users> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> findById(@PathVariable Long id) {
        Optional<Users> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Users> save(@RequestBody Users user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> update(@PathVariable Long id, @RequestBody Users user) {
        Optional<Users> existingUser = userService.findById(id);
        if (existingUser.isPresent()) {
            Users usersData = existingUser.get();
            usersData.setName(user.getName());
            usersData.setEmail(user.getEmail());
            usersData.setPassword(user.getPassword());

            return ResponseEntity.ok(userService.save(usersData));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Users> deleteById(@PathVariable Long id) {
        Optional<Users> user = userService.findById(id);

        if (user.isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
