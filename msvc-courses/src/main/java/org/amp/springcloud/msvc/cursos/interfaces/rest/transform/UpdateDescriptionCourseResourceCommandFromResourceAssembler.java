package org.amp.springcloud.msvc.cursos.interfaces.rest.transform;

import org.amp.springcloud.msvc.cursos.domain.model.commands.UpdateDescriptionCourseCommand;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.UpdateDescriptionCourseResource;

public class UpdateDescriptionCourseResourceCommandFromResourceAssembler {
    public static UpdateDescriptionCourseCommand toCommandFromResource(Long courseId, UpdateDescriptionCourseResource resource) {
        return new UpdateDescriptionCourseCommand(
                courseId,
                resource.description()
        );
    }
}