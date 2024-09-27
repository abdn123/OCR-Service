package com.example.Backend_Project.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.Backend_Project.Entities.User;
import com.example.Backend_Project.Repositories.UserRepository;
import com.example.Backend_Project.dto.LoginUserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    public User authenticate(LoginUserDto input) throws Exception {
        System.out.println("In Authentication Service");
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getUsername(),
                input.getPassword())
            );
        System.out.println("Username: " + input.getUsername());
        System.out.println("Password: " + input.getPassword());
        User user = userRepository.findByUsername(input.getUsername())
                .orElseThrow();
        
        if(!user.isActive())
            throw new Exception("User account not active.");
        else if(!user.isAccountNonExpired())
            throw new Exception("User Account Expired");
        else if(!user.isAccountNonLocked())
            throw new Exception("User Account Locked");

        return user;
    }
}
