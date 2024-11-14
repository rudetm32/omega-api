package com.omega.server.domain.user;

import com.omega.server.domain.company.CompanyBasicDTO;

public record ResponseUserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String username,
        Rol rol,
        CompanyBasicDTO companyBasicDTO
) {

}
