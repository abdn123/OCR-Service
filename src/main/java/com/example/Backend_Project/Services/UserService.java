package com.example.backend_project.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.backend_project.Entities.User;
import com.example.backend_project.Repositories.UserRepository;
import com.example.backend_project.dto.RegisterUserDto;
import com.example.backend_project.dto.ResetPasswordDto;

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
        
        User user = new User();
        
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(input.getPassword());
        user.setActive(input.isActive());
        user.setRole(input.getRole());
        
        if(input.getImage() != null)
            user.setImage(input.getImage());
        else
            user.setImage(null);

        return userRepository.save(user);
    }

    public void deleteUser(String username) {
        User userDetails = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("No User found for username -> " + username));
            userRepository.delete(userDetails);
    }

    public User updateUser(User user) {
        User update = userRepository.findById(user.getId())
        .orElseThrow(() -> new UsernameNotFoundException("No User found for username -> " + user.getUsername()));
        update.setUsername(user.getUsername());
        update.setEmail(user.getEmail());
        update.setActive(user.isActive());
        update.setRole(user.getRole());
        update.setImage(user.getImage());
        userRepository.save(update);

        return update;
    }

    public User resetPassword(ResetPasswordDto obj) throws Exception{
        User user = userRepository.findByUsername(obj.getUsername())
                .orElseThrow();

        if(!user.getPassword().equals(obj.getOldPassword())) 
            throw new Exception("Incorrect Old Password");
        else if(!obj.getNewPassword1().equals(obj.getNewPassword2()))
            throw new Exception("Passwords do not match");
        else {
            user.setPassword(obj.getNewPassword1());
            userRepository.save(user);
            return user;
        }
    }

    public long getCountActive() {
        return userRepository.countByActive(true);
    }

    public long getCount() {
        return userRepository.count();
    }

    public byte[] getImage(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow();

        return user.getImage();
    }
}