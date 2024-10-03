package com.example.Backend_Project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend_Project.Entities.User;
import com.example.Backend_Project.Services.UserService;
import com.example.Backend_Project.dto.ImageResponse;

import lombok.RequiredArgsConstructor;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class CurrentUser {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("In Current User Service");
        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<ImageResponse> getUserImage(@PathVariable Long id) {
        ImageResponse img = new ImageResponse();
        img.setImage(userService.getImage(id));
        return ResponseEntity.ok(img);
    }
}
