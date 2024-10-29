package com.omega.server.repository;

import com.omega.server.domain.company.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByIdAndIsDeletedFalse(Long companyId);

    // Consulta para traer las compañías con los usuarios asociados
    @Query("SELECT c FROM Company c LEFT JOIN FETCH c.users")
    List<Company> findAllWithUsers();

    Optional<Company> findByNameAndIsDeletedFalse(String name);

    @Query("SELECT c FROM Company c WHERE c.isDeleted = false")
    Page<Company> findAllActiveCompanies(Pageable pageable);
}
