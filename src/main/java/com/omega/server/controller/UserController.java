package com.omega.server.controller;

import com.omega.server.domain.company.IncludeCompanyInUserDTO;
import com.omega.server.domain.company.ResponseCompany;
import com.omega.server.domain.company.ResponseCompanyBasic;
import com.omega.server.domain.company.ResponseCompanyWithUsers;
import com.omega.server.domain.user.*;
import com.omega.server.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseUser> createUser(@RequestBody @Valid RegisterUser data, UriComponentsBuilder uri) {
        User newUser = new User(data);
        User newData = userService.createUser(newUser, data.companyId());
        ResponseUser response = new ResponseUser(
                newData.getId(), newData.getFirstName(), newData.getLastName(),
                newData.getEmail(), newData.getTelephone(), newData.getUsername(),
                newData.getRol(),
                new IncludeCompanyInUserDTO(
                        newData.getCompany().getId(), newData.getCompany().getName(),
                        newData.getCompany().getEmail(), newData.getCompany().getNameContact()
        ));

        URI url = uri.path("/user/{id}").buildAndExpand(newData.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserBasic>> getUsers(){
        List<User> users = userService.getAllUsers();
        var responseUser = users.stream()
                .map(user -> new ResponseUserBasic(
                        user.getId(), user.getFirstName(), user.getLastName(),
                        user.getEmail(), user.getTelephone(), user.getUsername(), user.getRol(),
                        new IncludeCompanyInUserDTO(
                                user.getCompany().getId(), user.getCompany().getName(),
                                user.getCompany().getEmail(), user.getCompany().getNameContact()))
                ).toList();
        return ResponseEntity.ok(responseUser);
    }
}