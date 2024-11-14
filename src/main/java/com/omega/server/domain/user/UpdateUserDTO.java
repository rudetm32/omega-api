package com.omega.server.domain.user;

public record UpdateUserDTO(
        String firstName,
        String lastname,
        String email,
        Rol rol,
        Boolean isDeleted
) {

}
