package com.example.Backend_Project.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Backend_Project.Entities.User;
import com.example.Backend_Project.Repositories.UserRepository;
import com.example.Backend_Project.dto.RegisterUserDto;

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

    public User signup(RegisterUserDto input) {
        User user = new User(
            input.getUsername(), input.getEmail(), input.getPassword(), input.isActive(), input.getRole()
        );

        return userRepository.save(user);
    }

    public void deleteUser(String username) {
        User userDetails = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("No User found for username -> " + username));
            userRepository.delete(userDetails);
    }

    public User updateUser(User user) {
        User update = userRepository.findByUsername(user.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("No User found for username -> " + user.getUsername()));
        update.setUsername(user.getUsername());
        update.setEmail(user.getEmail());
        update.setActive(user.isActive());
        update.setRole(user.getRole());
        userRepository.save(update);

        return update;
    }
}