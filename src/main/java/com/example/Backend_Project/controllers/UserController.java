package com.example.backend_project.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_project.dto.GetUsersDto;
import com.example.backend_project.dto.RegisterUserDto;
import com.example.backend_project.dto.ResetPasswordDto;
import com.example.backend_project.dto.UserMessageDto;
import com.example.backend_project.entities.User;
import com.example.backend_project.services.JwtService;
import com.example.backend_project.services.UserService;


@RequestMapping("/users")
@RestController
public class UserController {
    
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
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
    public ResponseEntity<UserMessageDto> deleteUser(@RequestHeader(value="Authorization") String auth, @RequestBody User user) {
        
        auth = auth.substring(7);
        UserMessageDto del = new UserMessageDto();
        
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
    public ResponseEntity<UserMessageDto> updateUser(@RequestHeader(value="Authorization") String auth, @RequestBody User user) {
        
        auth = auth.substring(7);
        UserMessageDto del = new UserMessageDto();
        
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
    
    @PostMapping("/resetpassword")
    public ResponseEntity<UserMessageDto> resetPassword(@RequestBody ResetPasswordDto request) {
        
        UserMessageDto response = new UserMessageDto();
        try {
            User user = userService.resetPassword(request);
            response.setMessage("Password Changed Successfully");
            response.setUsername(user.getUsername());
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getusers")
    public ResponseEntity<GetUsersDto> getUsers() {
        GetUsersDto response = new GetUsersDto();
        response.setTotalUsers(userService.getCount());
        response.setActiveUsers(userService.getCountActive());

        return ResponseEntity.ok(response);
    }
}