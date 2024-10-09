package com.omega.server.service;

import com.omega.server.domain.company.Company;
import com.omega.server.exception.CustomException;
import com.omega.server.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public Company createCompany(Company data) {
        try{

            return companyRepository.save(data);
        }catch(Exception e){

         throw new CustomException("Ocurrio un error al crear la compañia " + e.getMessage());
        }
    }

    public List<Company> getAllCompanies() {
        try{
            return companyRepository.findAll();
        } catch(Exception e){

            throw new CustomException("Ocurrio un error al obtener las compañias " + e.getMessage());
        }
    }

    public List<Company> getAllCompaniesWithUsers() {
        try{

            return companyRepository.findAllWithUsers();
        } catch(Exception e){

            throw new CustomException("Ocurrio un error al obtener las compañias" + e.getMessage());
        }
    }


}
