package com.omega.server.controller;

import com.omega.server.domain.user.AuthenticationUserDTO;
import com.omega.server.domain.user.JWTTokenDTO;
import com.omega.server.domain.user.User;
import com.omega.server.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid AuthenticationUserDTO authenticationUserDTO) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                authenticationUserDTO.username(),
                authenticationUserDTO.password());
        var authenticateUser = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generateToken((User) authenticateUser.getPrincipal());
        return ResponseEntity.ok(new JWTTokenDTO(JWTtoken));
    }
}
