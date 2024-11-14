package com.omega.server.domain.vehicle;

import com.omega.server.domain.company.ResponseCompaniesNameDTO;

public record ListVehiclesDTO(
        Long id,
        String plates,
        String ecoNum,
        String model,
        ResponseCompaniesNameDTO company // Ya contiene id y name de la compañía
) {
    // Constructor del DTO que acepta un objeto Vehicle y lo transforma en un DTO
    public ListVehiclesDTO(Vehicle vehicle) {
        this(
                vehicle.getId(),
                vehicle.getLicensePlate(),
                vehicle.getEcoNum(),
                vehicle.getModel(),
                new ResponseCompaniesNameDTO(vehicle.getCompany().getId(), vehicle.getCompany().getName())
        );
    }
}
