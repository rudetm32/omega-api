package com.omega.server.domain.address;

public record ResponseAddressDTO(
        Long id,
        String street,
        String number,
        String city,
        String state,
        String zipCode
) {
}
