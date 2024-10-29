package com.omega.server.domain.company;

import com.omega.server.domain.address.ResponseAddressDTO;

public record ListCompanyDTO(
        Long id,
        String name,
        String email,
        String telephone,
        String contactName,
        ResponseAddressDTO address
) {
    public ListCompanyDTO(Company company) {
        this(company.getId(), company.getName(), company.getEmail(),
                company.getTelephone(),company.getContactName(),
                new ResponseAddressDTO(
                        company.getAddress().getId(),
                        company.getAddress().getStreet(),
                        company.getAddress().getNumber(),
                        company.getAddress().getCity(),
                        company.getAddress().getState(),
                        company.getAddress().getZipCode()
                )
        );
    }
}