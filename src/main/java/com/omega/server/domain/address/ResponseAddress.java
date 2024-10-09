package com.omega.server.domain.address;

public record ResponseAddress(
        Long id,
        String street,
        String number,
        String city,
        String state,
        String zipCode
) {
}
