package org.amp.springcloud.msvc.cursos.interfaces.rest;

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
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.CourseResource;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.CreateCourseResource;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.UpdateDescriptionCourseResource;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.UpdateNameCourseResource;
import org.amp.springcloud.msvc.cursos.interfaces.rest.transform.CourseResourceFromEntityAssembler;
import org.amp.springcloud.msvc.cursos.interfaces.rest.transform.CreateCourseResourceCommandFromResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Courses", description = "Courses management")
@RequestMapping( value = "/api/courses", produces = APPLICATION_JSON_VALUE)
public class CoursesController {

    private final CourseCommandService courseCommandService;
    private final CourseQueryService courseQueryService;

    @Autowired
    public CoursesController(CourseCommandService courseCommandService, CourseQueryService courseQueryService) {
        this.courseCommandService = courseCommandService;
        this.courseQueryService = courseQueryService;
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



}
