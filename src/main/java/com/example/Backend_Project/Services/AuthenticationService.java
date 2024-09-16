package com.example.Backend_Project.Services;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Backend_Project.Entities.User;
import com.example.Backend_Project.Repositories.UserRepository;
import com.example.Backend_Project.dto.LoginUserDto;
import com.example.Backend_Project.dto.RegisterUserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder;

    public User signup(RegisterUserDto input) {
        User user = new User(
            input.getUsername(), input.getEmail(), input.getPassword(), input.isActive()
        );

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getUsername(),
                input.getPassword())
        );
        return userRepository.findUserByEmail(input.getEmail());
    }
}
