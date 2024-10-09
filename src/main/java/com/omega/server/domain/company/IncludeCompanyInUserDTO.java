package com.omega.server.domain.company;

import com.omega.server.domain.user.Rol;

public record IncludeCompanyInUserDTO(
        Long id,
        String name,
        String email,
        String nameContact
) {
}
