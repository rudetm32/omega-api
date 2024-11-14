package com.omega.server.domain.vehicle;



public record UpdateVehicleDTO(
        String licensePlate,
        String model,
        String ecoNum,
        Boolean isDeleted
) {
}
