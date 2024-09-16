package com.example.Backend_Project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend_Project.Repositories.UserDao;
import com.example.Backend_Project.config.JwtUtils;
import com.example.Backend_Project.dto.AunthenticationRequest;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(
        @RequestBody AunthenticationRequest request
    ) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        System.out.println(request.getEmail());
        final UserDetails user = userDao.findUserByEmail(request.getEmail());
        if (user != null) {
            return ResponseEntity.ok("AccessToken: "+jwtUtils.generateToken(user));
        }
        return ResponseEntity.status(400).body("some error has occurred");
    }
}
