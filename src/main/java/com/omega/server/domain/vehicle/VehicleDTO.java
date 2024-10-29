package com.omega.server.domain.vehicle;

import com.omega.server.domain.location.LocationDTO;

import java.util.List;

public record VehicleDTO(Long id, String licensePlate, String economicNumber, String model, List<LocationDTO> locations) {
    // El constructor es implícito, no necesitas implementarlo.
}
