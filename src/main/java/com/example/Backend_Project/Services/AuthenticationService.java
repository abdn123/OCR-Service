package com.example.backend_project.services;

import javax.security.auth.login.AccountLockedException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.backend_project.dto.LoginUserDto;
import com.example.backend_project.entities.User;
import com.example.backend_project.repositories.UserRepository;

@Service
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public User authenticate(LoginUserDto input) throws Exception {
        System.out.println("In Authentication Service");
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getUsername(),
                input.getPassword())
            );
        System.out.println("Username: " + input.getUsername());
        System.out.println("Password: " + input.getPassword());

        User user = userRepository.findByUsername(input.getUsername());

        if(user == null) 
            throw new NullPointerException("Account Not Found.");
        else if(!user.isActive())
            throw new AccountLockedException("User account not active.");
        else if(!user.isAccountNonLocked())
            throw new AccountLockedException("User Account Locked.");
        return user;
    }
}
