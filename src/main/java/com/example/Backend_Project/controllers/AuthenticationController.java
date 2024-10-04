package com.example.backend_project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_project.Entities.User;
import com.example.backend_project.Services.AuthenticationService;
import com.example.backend_project.Services.JwtService;
import com.example.backend_project.dto.LoginResponseDto;
import com.example.backend_project.dto.LoginUserDto;

import lombok.RequiredArgsConstructor;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;    

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(
        @RequestBody LoginUserDto request
    ) {
        
        LoginResponseDto loginResponse = new LoginResponseDto();
        try {
            User registeredUser = authenticationService.authenticate(request);
            if (registeredUser == null) {
                return ResponseEntity.status(401).body(new LoginResponseDto("Incorrect Username or Password"));    
            }
            String jwtToken = jwtService.generateToken(registeredUser);
            loginResponse.setToken(jwtToken);
            loginResponse.setSetExpiresIn(jwtService.extractExpiration(jwtToken));
            return ResponseEntity.ok(loginResponse);
        } catch(BadCredentialsException | InternalAuthenticationServiceException e) {
            return ResponseEntity.status(401).body(new LoginResponseDto("Incorrect Username or Password"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new LoginResponseDto(e.getMessage()));
        }
    }
}
