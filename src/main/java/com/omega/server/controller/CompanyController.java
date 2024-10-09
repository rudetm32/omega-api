package com.omega.server.controller;

import com.omega.server.domain.address.RegisterAddress;
import com.omega.server.domain.company.*;
import com.omega.server.domain.user.UserDTO;
import com.omega.server.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseCompany> createCompany(@RequestBody @Valid RegisterCompany data, UriComponentsBuilder uri) {
        Company newCompany = new Company(data);
        Company newData = companyService.createCompany(newCompany);

        var response = new ResponseCompany(
                newData.getId(), newData.getName(), newData.getEmail(),
                newData.getTelephone(), newData.getNameContact(),
                new RegisterAddress(
                        newData.getAddress().getId(), newData.getAddress().getStreet(), newData.getAddress().getNumber(),
                        newData.getAddress().getCity(), newData.getAddress().getState(), newData.getAddress().getZipCode(),
                        newData.getAddress().isDeleted()));

        URI url = uri.path("/company/{id}").buildAndExpand(newData.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponseCompanyBasic>> getCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        List<ResponseCompanyBasic> responseCompanies = companies.stream()
                .map(company -> new ResponseCompanyBasic(
                        company.getId(), company.getName(), company.getEmail(),
                        company.getTelephone(), company.getNameContact(),
                        new AddressDTO(
                                company.getAddress().getId(), company.getAddress().getStreet(),
                                company.getAddress().getCity(), company.getAddress().getState(),
                                company.getAddress().getZipCode()))
                ).toList();
        return ResponseEntity.ok(responseCompanies);
    }

    @GetMapping("/with-users")
    public ResponseEntity<List<ResponseCompanyWithUsers>> getCompaniesWithUsers() {
        List<Company> companies = companyService.getAllCompaniesWithUsers();
        List<ResponseCompanyWithUsers> responseCompanies = companies.stream()
                .map(company -> new ResponseCompanyWithUsers(
                        company.getId(), company.getName(), company.getEmail(),
                        company.getTelephone(), company.getNameContact(),
                        company.getUsers().stream()
                                .map(user -> new UserDTO(
                                        user.getId(), user.getUsername(), user.getEmail()
                                )).toList()
                )).toList();
        return ResponseEntity.ok(responseCompanies);
    }

    @GetMapping("/names")
    public ResponseEntity<List<ResponseCompaniesNameDTO>> getCompaniesName() {
        List<Company> companies = companyService.getAllCompanies();
        List<ResponseCompaniesNameDTO> responseCompanies = companies.stream()
                .map(company -> new ResponseCompaniesNameDTO(
                        company.getId(), company.getName())
                ).toList();
        return ResponseEntity.ok(responseCompanies);
    }
}