package com.example.Backend_Project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend_Project.Entities.User;
import com.example.Backend_Project.Services.AuthenticationService;
import com.example.Backend_Project.Services.JwtService;
import com.example.Backend_Project.dto.LoginResponse;
import com.example.Backend_Project.dto.LoginUserDto;

import lombok.RequiredArgsConstructor;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
        @RequestBody LoginUserDto request
    ) {

        User registeredUser = authenticationService.authenticate(request);
        
        LoginResponse loginResponse = new LoginResponse();
        
        String jwtToken = jwtService.generateToken(registeredUser);
        
        
        if (registeredUser != null) {
            
            loginResponse.setToken(jwtToken);
            loginResponse.setSetExpiresIn(jwtService.extractExpiration(jwtToken));
            return ResponseEntity.ok(loginResponse);
            
    }

        
        return ResponseEntity.status(401).body(loginResponse);
    }
}
