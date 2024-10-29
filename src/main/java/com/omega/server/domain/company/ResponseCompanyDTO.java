package com.omega.server.domain.company;


import com.omega.server.domain.address.AddressDTO;


public record ResponseCompanyDTO(
        Long id,
        String name,
        String email,
        String telephone,
        String contactName,
        AddressDTO address
) {
}
