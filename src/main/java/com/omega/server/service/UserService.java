package com.omega.server.service;

import com.omega.server.domain.company.Company;
import com.omega.server.exception.CustomException;
import com.omega.server.repository.CompanyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.omega.server.domain.user.User;
import com.omega.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public UserService(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            CompanyRepository companyRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }


    public User createUser(User data, Long companyId) {
        try {
            String encodedPassword = passwordEncoder.encode(data.getPassword());
            data.setPassword(encodedPassword);


            Optional<User> existsUser = userRepository.findByUsernameAndIsDeletedFalse(data.getUsername());


            if (existsUser.isPresent()) {
                throw new CustomException("El usuario con ese nombre ya existe");
            }
            Company company = companyRepository.findByIdAndIsDeletedFalse(companyId)
                    .orElseThrow(() -> new CustomException("Compañía no encontrada o inactiva."));


            // Asignar la compañía al nuevo usuario
            data.setCompany(company);
            return userRepository.save(data);


        } catch(Exception e) {
            throw new CustomException("Ocurrió un error al crear el usuario: " + e.getMessage(), e);
        }
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch(Exception e) {
            throw new CustomException("Ocurrio un error al obtener los usuarios " + e.getMessage());
        }
    }
}
