package com.omega.server.domain.location;

import java.util.List;

public record VehicleWithLocationDTO(
        Long id,
        String licensePlate,
        String EconomicNumber,
        String model,
        List<Location> locations
) {
}
