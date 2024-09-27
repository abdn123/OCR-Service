package com.example.Backend_Project.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend_Project.Entities.User;
import com.example.Backend_Project.Services.JwtService;
import com.example.Backend_Project.Services.UserService;
import com.example.Backend_Project.dto.RegisterUserDto;
import com.example.Backend_Project.dto.UserMessage;

import lombok.RequiredArgsConstructor;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();
        System.out.println("In User Mapping");
        return ResponseEntity.ok(users);
    }

    @PostMapping("/newuser")
    public ResponseEntity<User> signup(@RequestBody RegisterUserDto request) {
        System.out.println(request);
        User registeredUser = userService.signup(request);
        System.out.println("In New User Mapping");
        return ResponseEntity.ok(registeredUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<UserMessage> deleteUser(@RequestHeader(value="Authorization") String auth, @RequestBody User user) {
        
        auth = auth.substring(7);
        UserMessage del = new UserMessage();
        
        if(jwtService.extractUsername(auth).equals(user.getUsername())) {
            del.setMessage("Unable to delete, user is currently active");
            del.setUsername(user.getUsername());
            return ResponseEntity.status(403).body(del);    
        }

        System.out.println("In Delete User Service");

        userService.deleteUser(user.getUsername());
        del.setMessage("User Deleted Successfully");
        del.setUsername(user.getUsername());
        return ResponseEntity.ok(del);
    }
    
    @PostMapping("/update")
    public ResponseEntity<UserMessage> updateUser(@RequestHeader(value="Authorization") String auth, @RequestBody User user) {
        
        auth = auth.substring(7);
        UserMessage del = new UserMessage();
        
        if(jwtService.extractUsername(auth).equals(user.getUsername())) {
            del.setMessage("Unable to update, user is currently active");
            del.setUsername(user.getUsername());
            return ResponseEntity.status(403).body(del);    
        }
        
        userService.updateUser(user);
        del.setMessage("User Updated Successfully");
        del.setUsername(user.getUsername());

        return ResponseEntity.ok(del);
    }
    
}