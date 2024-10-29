package com.omega.server.controller;

import com.omega.server.domain.company.*;
import com.omega.server.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @PostMapping
    public ResponseEntity<ResponseCompanyDTO> createCompany(
            @RequestBody @Valid CompanyDTO companyDTO,
            UriComponentsBuilder uriComponentBuilder) {
        ResponseCompanyDTO response = companyService.createCompanyAndMap(companyDTO);
        URI location = uriComponentBuilder.path("/company/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ListCompanyDTO>> listCompanies(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        Page<Company> companies = companyService.listCompanySort(pageable);
        Page<ListCompanyDTO> listCompanies = companies.map(ListCompanyDTO::new);
        return ResponseEntity.ok(listCompanies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCompanyDTO> getCompanyById(@PathVariable Long id){
        ResponseCompanyDTO response = companyService.findCompany(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletedCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long companyId, @RequestBody UpdateCompanyDTO updateCompanyDTO) {
        var companyUpdate = companyService.updateCompany(companyId, updateCompanyDTO);
        return ResponseEntity.ok(companyUpdate);
    }

}