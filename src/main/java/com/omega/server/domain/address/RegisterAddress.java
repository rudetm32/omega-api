package com.omega.server.domain.address;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterAddress(
        Long id,
        String street,
        String number,
        String city,
        String state,
        String zipCode,

        @JsonProperty(defaultValue = "false")
        Boolean isDeleted
) {
    public RegisterAddress {
        if (isDeleted == null) {
            isDeleted = false;
        }
    }

}
