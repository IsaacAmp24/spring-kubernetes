package org.amp.springcloud.msvc.users.interfaces.rest.transform;

import org.amp.springcloud.msvc.users.domain.model.commands.CreateUserCommand;
import org.amp.springcloud.msvc.users.interfaces.rest.resources.CreateUserResource;

public class CreateUserResourceCommandFromResourceAssembler {
    public static CreateUserCommand toCommandFromResource(CreateUserResource resource) {
        return new CreateUserCommand(
                resource.name(),
                resource.email(),
                resource.password()
        );
    }
}
