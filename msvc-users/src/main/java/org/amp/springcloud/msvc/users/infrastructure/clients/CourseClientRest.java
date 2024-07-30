package org.amp.springcloud.msvc.users.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-courses", url = "http://localhost:8002/api/courses")
public interface CourseClientRest {

    @DeleteMapping("/unassign-student/{userId}")
    void unassignUserFromCourses(@PathVariable("userId") Long userId);

}