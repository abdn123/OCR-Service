package com.example.Backend_Project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend_Project.Entities.User;
import com.example.Backend_Project.Repositories.UserDao;
import com.example.Backend_Project.Services.AuthenticationService;
import com.example.Backend_Project.config.JwtUtils;
import com.example.Backend_Project.dto.LoginUserDto;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<User> authenticate(
        @RequestBody LoginUserDto request
    ) {
        User registeredUser = authenticationService.authenticate(request);
        
        String jwtToken = jwtUtils.generateToken(registeredUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        if (registeredUser != null) {
            return ResponseEntity.ok(jwtToken);
        }
        return ResponseEntity.status(400).body("some error has occurred");
    }
}
