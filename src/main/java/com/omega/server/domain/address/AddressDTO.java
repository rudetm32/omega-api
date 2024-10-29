package com.omega.server.domain.address;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddressDTO(
        Long id,
        String street,
        String number,
        String city,
        String state,
        String zipCode,

        @JsonProperty(defaultValue = "false")
        Boolean isDeleted
) {
    public AddressDTO {
        if (isDeleted == null) {
            isDeleted = false;
        }
    }

}
