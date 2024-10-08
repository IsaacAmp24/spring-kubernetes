package org.amp.springcloud.msvc.users.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

// para local
// @FeignClient(name = "msvc-courses", url = "http://localhost:8002/api/courses")

// para docker
@FeignClient(name = "msvc-courses", url = "msvc-courses:8002/api/courses")
public interface CourseClientRest {

    @DeleteMapping("/unassign-student/{userId}")
    void unassignUserFromCourses(@PathVariable("userId") Long userId);

}