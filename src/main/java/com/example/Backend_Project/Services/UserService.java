package com.example.Backend_Project.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Backend_Project.Entities.User;
import com.example.Backend_Project.Repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        
        userRepository.findAll().forEach(users::add);

        return users;
    }
}