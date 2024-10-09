package com.omega.server.domain.company;



public record ResponseCompany(
        Long id,
        String name,
        String email,
        String telephone,
        String nameContact,
        Object address  // Usa Object para aceptar diferentes tipos de DTOs
) {
}
