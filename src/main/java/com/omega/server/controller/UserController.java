package com.omega.server.controller;

import com.omega.server.domain.user.*;
import com.omega.server.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseUserDTO> createUser(@RequestBody @Valid UserDTO userDTO,
            UriComponentsBuilder uriComponentsBuilder) {
        ResponseUserDTO response = userService.createUserAndMap(userDTO);
        URI location = uriComponentsBuilder.path("/user/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ListUserDTO>> listVehicles(@PageableDefault(size = 10, sort = "username") Pageable pageable) {
        Page<User> users = userService.listUserSort(pageable);
        Page<ListUserDTO> listUsers = users.map(ListUserDTO::new);
        return ResponseEntity.ok(listUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> getUserById (@PathVariable Long id) {
        ResponseUserDTO response = userService.findUser(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletedUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UpdateUserDTO updateUserDTO) {
        var userUpdate = userService.updateUser(userId, updateUserDTO);
        return ResponseEntity.ok(userUpdate);
    }
}
