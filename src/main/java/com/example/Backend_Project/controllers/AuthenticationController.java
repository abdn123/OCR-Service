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
        
        LoginResponse loginResponse = new LoginResponse();
        try {
            User registeredUser = authenticationService.authenticate(request);
            if (registeredUser == null) {
                return ResponseEntity.status(401).body(new LoginResponse("Incorrect Username or Password"));    
            }
            String jwtToken = jwtService.generateToken(registeredUser);
            loginResponse.setToken(jwtToken);
            loginResponse.setSetExpiresIn(jwtService.extractExpiration(jwtToken));
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new LoginResponse(e.getMessage()));
        }
        
        
        

        

    }
}
