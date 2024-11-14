package com.omega.server.service;

import com.omega.server.domain.address.AddressDTO;
import com.omega.server.domain.company.Company;
import com.omega.server.domain.company.CompanyDTO;
import com.omega.server.domain.company.ResponseCompanyDTO;
import com.omega.server.domain.company.UpdateCompanyDTO;
import com.omega.server.infra.exception.CustomException;
import com.omega.server.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }


    public ResponseCompanyDTO createCompanyAndMap(CompanyDTO companyDTO) {
        Optional<Company> existsCompany = companyRepository.findByNameAndIsDeletedFalse(companyDTO.name());
        if (existsCompany.isPresent()) {
            throw new CustomException("A company with that name already exists.");
        }

        Company newCompany = new Company(companyDTO);
        Company savedCompany = companyRepository.save(newCompany);

        return new ResponseCompanyDTO(savedCompany.getId(), savedCompany.getName(),
                savedCompany.getEmail(), savedCompany.getContactName(),savedCompany.getTelephone(),
                new AddressDTO(
                        savedCompany.getAddress().getId(),
                        savedCompany.getAddress().getStreet(),
                        savedCompany.getAddress().getNumber(),
                        savedCompany.getAddress().getCity(),
                        savedCompany.getAddress().getState(),
                        savedCompany.getAddress().getZipCode(),
                        savedCompany.getAddress().isDeleted()
                ));
    }

    public Page<Company> listCompanySort(Pageable pageable) {
        var companies =  companyRepository.findAllActiveCompanies(pageable);
        if (companies.isEmpty()) {
            throw new CustomException("No active companies found.");
        }
        return companies;
    }

    public ResponseCompanyDTO findCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CustomException("Company not found"));
        if (Boolean.TRUE.equals(company.getDeleted())){
            throw new CustomException("Company with ID: " + id + "is deactivated");
        } else {
            return new ResponseCompanyDTO(company.getId(), company.getName(),
                    company.getEmail(), company.getTelephone(),company.getContactName(),
                    new AddressDTO(
                            company.getAddress().getId(),
                            company.getAddress().getStreet(),
                            company.getAddress().getNumber(),
                            company.getAddress().getCity(),
                            company.getAddress().getState(),
                            company.getAddress().getZipCode(),
                            company.getAddress().isDeleted())
            );
        }
    }

    public void deleteCompany(Long companyId) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        companyOptional.ifPresentOrElse(company -> { company.setDeleted(true);
            companyRepository.save(company);
        }, () -> {
            throw new CustomException("Company not found with ID: "+ companyId);
        });
    }

    public Company updateCompany(Long companyId, UpdateCompanyDTO updateCompanyDTO) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);

        return companyOptional.map( company -> {
            company.updateCompanyAttributes(updateCompanyDTO);
            return companyRepository.save(company);
        }).orElseThrow(() -> new CustomException("Company not found with ID: " +companyId));
    }
}
