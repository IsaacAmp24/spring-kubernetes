package org.amp.springcloud.msvc.users.domain.model.queries;

import java.util.List;

// Query para obtener todos los usuarios por su id
public record GetAllUsersByIdQuery(
        List<Long> ids
) {
}
