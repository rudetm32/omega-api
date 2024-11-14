package com.omega.server.domain.user;

public record AuthenticationUserDTO(
        String username,
        String password
) {
}
