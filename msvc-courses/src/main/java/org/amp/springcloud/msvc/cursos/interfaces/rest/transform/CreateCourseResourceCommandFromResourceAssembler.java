package org.amp.springcloud.msvc.cursos.interfaces.rest.transform;

import org.amp.springcloud.msvc.cursos.domain.model.commands.CreateCourseCommand;
import org.amp.springcloud.msvc.cursos.interfaces.rest.resources.CreateCourseResource;

public class CreateCourseResourceCommandFromResourceAssembler {
    public static CreateCourseCommand toCommandFromResource(CreateCourseResource courseResource) {
        return new CreateCourseCommand(
                courseResource.name(),
                courseResource.description()
        );
    }
}
