package com.omega.server.domain.company;

import com.omega.server.domain.company.AddressDTO;

public record ResponseCompanyBasic(
        Long id,
        String name,
        String email,
        String telephone,
        String nameContact,
        AddressDTO address
) {
}
