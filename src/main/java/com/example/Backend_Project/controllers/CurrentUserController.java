package com.example.backend_project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_project.dto.ImageResponse;
import com.example.backend_project.entities.User;
import com.example.backend_project.services.UserService;

@RequestMapping
@RestController
public class CurrentUserController {

    private final UserService userService;

    public CurrentUserController(UserService userService) {
        this.userService = userService;
    }

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
