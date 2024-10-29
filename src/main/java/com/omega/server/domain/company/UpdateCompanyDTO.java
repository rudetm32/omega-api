package com.omega.server.domain.company;

import com.omega.server.domain.address.ResponseAddressDTO;

public record UpdateCompanyDTO(
        String email,
        String telephone,
        String contactName,
        Boolean isDeleted,
        ResponseAddressDTO address
) {
}
