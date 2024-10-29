package com.omega.server.domain.user;

import com.omega.server.domain.company.CompanyBasicDTO;

public record ListUserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String telephone,
        String username,
        Rol rol,
        CompanyBasicDTO company

) {
    public ListUserDTO(User user){
        this(user.getId(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getTelephone(), user.getUsername(),
                user.getRol(),
                new CompanyBasicDTO(user.getCompany().getId(), user.getCompany().getName(),
                        user.getCompany().getEmail(), user.getCompany().getContactName()
                )
        );
    }
}
