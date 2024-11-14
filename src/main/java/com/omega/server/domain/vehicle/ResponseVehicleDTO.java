package com.omega.server.domain.vehicle;

import com.omega.server.domain.company.ResponseCompaniesNameDTO;

public record ResponseVehicleDTO(
        Long id,
        String plates,
        String ecoNum,
        String model,
        ResponseCompaniesNameDTO company
) {
}
