package com.omega.server.domain.company;

public record CompanyBasicDTO(
        Long id,
        String name,
        String email,
        String contactName
) {
}
