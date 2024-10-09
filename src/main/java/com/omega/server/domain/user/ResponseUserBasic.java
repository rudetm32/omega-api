package com.omega.server.domain.user;

import com.omega.server.domain.company.IncludeCompanyInUserDTO;

public record ResponseUserBasic(
        Long id,
        String firstName,
        String lastName,
        String email,
        String telephone,
        String username,
        Rol rol,
        IncludeCompanyInUserDTO company

) {
}
