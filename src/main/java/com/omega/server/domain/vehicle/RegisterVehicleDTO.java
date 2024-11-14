package com.omega.server.domain.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterVehicleDTO(

        @NotBlank(message = "Vehicle plates must be provided")
        String licensePlate,

        @NotBlank(message = "Vehicle model must be provided")
        String model,

        @NotBlank(message = "Vehicle economic number must be provided")
        String ecoNum,

        @NotNull(message = "Company ID must be provided")
        Long companyId,

        Boolean isDeleted
) {

        public RegisterVehicleDTO {
                if (isDeleted == null) {
                        isDeleted = false;
                }
        }
}
