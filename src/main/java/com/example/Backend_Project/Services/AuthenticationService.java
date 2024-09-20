package com.example.Backend_Project.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.Backend_Project.Entities.User;
import com.example.Backend_Project.Repositories.UserRepository;
import com.example.Backend_Project.dto.LoginUserDto;
import com.example.Backend_Project.dto.RegisterUserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    public User signup(RegisterUserDto input) {
        User user = new User(
            input.getUsername(), input.getEmail(), input.getPassword(), input.isActive(), input.getRole()
        );

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        System.out.println("In Authentication Service");
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getUsername(),
                input.getPassword())
            );
        System.out.println("Username: " + input.getUsername());
        System.out.println("Password: " + input.getPassword());
        return userRepository.findByUsername(input.getUsername())
                .orElseThrow();
    }
}
