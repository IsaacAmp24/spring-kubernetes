package org.amp.springcloud.msvc.users.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-courses", url = "localhost:8002")
public interface CourseClientRest {

    @DeleteMapping("/api/courses/delete-user-from-course/{userId}")
    void deleteUserFromCourse(@PathVariable("id") Long userId);

}