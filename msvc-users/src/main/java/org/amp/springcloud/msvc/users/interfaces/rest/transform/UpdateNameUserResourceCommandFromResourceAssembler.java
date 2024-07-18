package org.amp.springcloud.msvc.users.interfaces.rest.transform;

import org.amp.springcloud.msvc.users.domain.model.commands.UpdateNameUserCommand;
import org.amp.springcloud.msvc.users.interfaces.rest.resources.UpdateNameUserResource;

public class UpdateNameUserResourceCommandFromResourceAssembler {
    public static UpdateNameUserCommand toCommandFromResource(Long userId, UpdateNameUserResource resource) {
        return new UpdateNameUserCommand(
                userId,
                resource.name()
        );
    }
}
