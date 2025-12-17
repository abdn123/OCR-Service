package com.example.backend_project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend_project.dto.RegisterUserDto;
import com.example.backend_project.dto.ResetPasswordDto;
import com.example.backend_project.entities.User;
import com.example.backend_project.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    static BadCredentialsException badCredException = new BadCredentialsException("Bad Credentials");

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setActive(input.isActive());
        user.setRole(input.getRole());
        user.setDocCount(Long.valueOf(0));

        if (input.getImage() != null)
            user.setImage(input.getImage());
        else
            user.setImage(null);

        return userRepository.save(user);
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw badCredException;
        else
            userRepository.delete(user);
    }

    public User updateUser(User user) {
        User update = userRepository.findById(user.getId())
                .orElseThrow(() -> badCredException);
        update.setUsername(user.getUsername());
        update.setEmail(user.getEmail());
        update.setActive(user.isActive());
        update.setRole(user.getRole());
        update.setImage(user.getImage());
        userRepository.save(update);

        return update;
    }

    public User resetPassword(ResetPasswordDto obj) throws Exception {
        User user = userRepository.findByUsername(obj.getUsername());

        if (user == null)
            throw badCredException;
        if (!passwordEncoder.matches(obj.getOldPassword(), user.getPassword()))
            throw new Exception("Incorrect Old Password");
        else if (!obj.getNewPassword1().equals(obj.getNewPassword2()))
            throw new Exception("Passwords do not match");
        else {
            user.setPassword(passwordEncoder.encode(obj.getNewPassword1()));
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