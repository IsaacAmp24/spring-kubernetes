package org.amp.springcloud.msvc.cursos.interfaces.rest;

import feign.FeignException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.amp.springcloud.msvc.cursos.domain.model.aggregates.Course;
import org.amp.springcloud.msvc.cursos.domain.model.commands.DeleteCourseCommand;
import org.amp.springcloud.msvc.cursos.domain.model.commands.UpdateDescriptionCourseCommand;
import org.amp.springcloud.msvc.cursos.domain.model.commands.UpdateNameCourseCommand;
import org.amp.springcloud.msvc.cursos.domain.model.queries.GetAllCoursesQuery;
import org.amp.springcloud.msvc.cursos.domain.model.queries.GetCourseByIdQuery;
import org.amp.springcloud.msvc.cursos.domain.services.CourseCommandService;
import org.amp.springcloud.msvc.cursos.domain.services.CourseQueryService;
import org.amp.springcloud.msvc.cursos.domain.services.CourseUserService;
import org.amp.springcloud.msvc.cursos.infrastructure.clients.UserClientRest;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.*;
import org.amp.springcloud.msvc.cursos.interfaces.rest.transform.CourseResourceFromEntityAssembler;
import org.amp.springcloud.msvc.cursos.interfaces.rest.transform.CreateCourseResourceCommandFromResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Courses", description = "Courses management")
@RequestMapping( value = "/api/courses", produces = APPLICATION_JSON_VALUE)
public class CoursesController {

    
    private final CourseCommandService courseCommandService;
    private final CourseQueryService courseQueryService;
    private final CourseUserService courseUserService;
    private final UserClientRest userClientRest;

    
    public CoursesController(CourseCommandService courseCommandService, CourseQueryService courseQueryService, CourseUserService courseUserService, UserClientRest userClientRest) {
        this.courseCommandService = courseCommandService;
        this.courseQueryService = courseQueryService;
        this.courseUserService = courseUserService;
        this.userClientRest = userClientRest;
    }

    @GetMapping
    public ResponseEntity<List<CourseResource>> findAll() {
        var getAllCoursesQuery = new GetAllCoursesQuery();
        var courses = courseQueryService.handle(getAllCoursesQuery);

        var courseResources = courses.stream()
                .map(CourseResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(courseResources);

    }

    @PostMapping
    public ResponseEntity<CourseResource> createCourse(@Valid @RequestBody CreateCourseResource createCourseResource) {
        var createCourseCommand = CreateCourseResourceCommandFromResourceAssembler.toCommandFromResource(createCourseResource);
        var courseId = courseCommandService.handle(createCourseCommand);

        return courseQueryService.handle(new GetCourseByIdQuery(courseId))
                .map(courseEntity -> new ResponseEntity<>
                        (CourseResourceFromEntityAssembler.toResourceFromEntity(courseEntity),
                                HttpStatus.CREATED))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResource> getCourseById(@PathVariable Long courseId) {
        var getCourseByIdQuery = new GetCourseByIdQuery(courseId);
        var course = courseQueryService.handle(getCourseByIdQuery);

        return course.map(courseEntity -> ResponseEntity.ok(
                        CourseResourceFromEntityAssembler.toResourceFromEntity(courseEntity)))
                .orElse(ResponseEntity.notFound().header(
                        "message", "Course with ID " + courseId + " not found")
                        .build());
    }

    @PatchMapping("/{courseId}/name")
    public ResponseEntity<CourseResource> updateCourseName(
            @PathVariable Long courseId,
            @RequestBody UpdateNameCourseResource resource) {

        var updateCourseNameCommand = new UpdateNameCourseCommand(
                courseId,
                resource.name()
        );

        Optional<Course> updatedCourse = courseCommandService.handle(updateCourseNameCommand);

        if (updatedCourse.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var courseResource = CourseResourceFromEntityAssembler.toResourceFromEntity(updatedCourse.get());
        return ResponseEntity.ok(courseResource);
    }

    @PatchMapping("/{courseId}/description")
    public ResponseEntity<CourseResource> updateCourseDescription(
            @PathVariable Long courseId,
            @RequestBody UpdateDescriptionCourseResource resource) {

        var updateCourseDescriptionCommand = new UpdateDescriptionCourseCommand(
                courseId,
                resource.description()
        );

        Optional<Course> updatedCourse = courseCommandService.handle(updateCourseDescriptionCommand);

        if (updatedCourse.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var courseResource = CourseResourceFromEntityAssembler.toResourceFromEntity(updatedCourse.get());
        return ResponseEntity.ok(courseResource);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        var deleteCourseCommand = new DeleteCourseCommand(courseId);
        courseCommandService.handle(deleteCourseCommand);

        return ResponseEntity.ok("Course with id " + courseId + " deleted successfully");
    }

    // metodos nuevos para la comunicacion con el servicio de alumnos

    // asignar un alumno a un curso
    @PutMapping("/assign-student/{courseId}")
    public ResponseEntity<?> assignUserToCourse(@RequestBody UserDTO userDTO, @PathVariable Long courseId) {

        Optional<UserDTO> user;

        try {
            user = courseUserService.assignUserToCourse(userDTO, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("No se encontro el usuario para asignarlo al curso" + "Message: " + e.getMessage());
        }

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    // crear un usuario para un curso
    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUserForCourse(@RequestBody CreateUserDTO createUserDTO, @PathVariable Long courseId) {

        Optional<CreateUserDTO> user;

        try {
            user = courseUserService.createUserByCourse(createUserDTO, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("No se pudo crear el usuario" + "Message: " + e.getMessage());
        }

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el usuario");
        }

    }

    // desasignamos un usuario de un curso
    @DeleteMapping("/unassign-student/{userId}")
    public ResponseEntity<Void> unassignUserFromCourses(@PathVariable Long userId) {
        courseUserService.unassignUserFromCourse(userId);
        return ResponseEntity.noContent().build();
    }

    // obtener todos los usuarios de un curso
    @GetMapping("/all-users/{courseId}")
    public ResponseEntity<?> getAllUsersByCourse(@PathVariable Long courseId) {
        Optional<Course> course;

        try {
            course = courseUserService.getAllUsersByCourse(courseId);
        } catch (FeignException e) {
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("No se encontro el curso; " + "Message: " + e.getMessage());
        }

        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // eliminamos el usuario de un curso
    @DeleteMapping("/delete-user-from-course/{userId}")
    public ResponseEntity<?> deleteUserFromCourse(@PathVariable Long userId) {
        userClientRest.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
