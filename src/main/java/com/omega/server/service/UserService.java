package com.omega.server.service;

import com.omega.server.domain.company.Company;
import com.omega.server.domain.company.CompanyBasicDTO;
import com.omega.server.domain.user.UserDTO;
import com.omega.server.domain.user.ResponseUserDTO;
import com.omega.server.domain.user.UpdateUserDTO;
import com.omega.server.infra.exception.CustomException;
import com.omega.server.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.omega.server.domain.user.User;
import com.omega.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository,
            CompanyRepository companyRepository) {

        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }


    public ResponseUserDTO createUserAndMap(UserDTO userDTO) {
        Company company = companyRepository.findByIdAndIsDeletedFalse(userDTO.companyId())
                .orElseThrow(() -> new CustomException("Company not found or inactive."));

        User newUser = new User(userDTO);
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);

//        Optional<User> existsUser = userRepository.findByUsernameAndIsDeletedFalse(newUser.getUsername());
//        if (existsUser.isPresent()) {
//            throw new CustomException("A user with that username already exists.");
//        }
        newUser.setCompany(company);
        newUser = userRepository.save(newUser);

        return new ResponseUserDTO(
                newUser.getId(), newUser.getFirstName(), newUser.getLastName(),
                newUser.getEmail(),  newUser.getUsername(), newUser.getRol(),
                new CompanyBasicDTO(
                        newUser.getCompany().getId(), newUser.getCompany().getName(),
                        newUser.getCompany().getEmail(), newUser.getCompany().getContactName()
                )
        );
    }

    public Page<User> listUserSort(Pageable pageable) {
        var users = userRepository.findAllActiveUsers(pageable);

        if (users.isEmpty()) {
            throw new CustomException("No active users found.");
        }
        return users;
    }

    public ResponseUserDTO findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found"));

        if(Boolean.TRUE.equals(user.getDeleted())){
            throw new CustomException("User with ID: " + id + " is deactivated");
        } else {
            return new ResponseUserDTO(
                    user.getId(), user.getFirstName(), user.getLastName(),
                    user.getEmail(), user.getUsername(), user.getRol(),
                    new CompanyBasicDTO(
                            user.getCompany().getId(), user.getCompany().getContactName(),
                            user.getCompany().getContactName(),user.getCompany().getEmail())
            );
        }
    }

    public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.ifPresentOrElse(user -> {
            user.setDeleted(true);
            userRepository.save(user);
            }, () -> {
                throw new CustomException("User not found with ID: " + id);
        });
    }

    public User updateUser(Long userId, UpdateUserDTO updateUserDTO){
        Optional<User> userOptional = userRepository.findById(userId);

        return userOptional.map(user -> {
            user.updateUserAttributes(updateUserDTO);
            return userRepository.save(user);
        }).orElseThrow(() -> new CustomException("User not found with ID: " + userId));
    }
}
