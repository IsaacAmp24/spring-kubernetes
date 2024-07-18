package org.amp.springcloud.msvc.cursos.interfaces.rest.transform;

import org.amp.springcloud.msvc.cursos.domain.model.commands.UpdateNameCourseCommand;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.UpdateNameCourseResource;

public class UpdateNameCourseResourceCommandFromResourceAssembler {
    public static UpdateNameCourseCommand toCommandFromResource(Long courseId, UpdateNameCourseResource resource) {
        return new UpdateNameCourseCommand(
                courseId,
                resource.name()
        );
    }
}
