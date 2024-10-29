package com.omega.server.domain.user;

public record UpdateUserDTO(
        String firstName,
        String lastname,
        String email,
        String telephone,
        Rol rol,
        Boolean isDeleted
) {

}
