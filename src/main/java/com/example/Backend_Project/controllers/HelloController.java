package com.example.Backend_Project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello From API");
    }

    @GetMapping("/say-goodbye")
    public ResponseEntity<String> getMethodName() {
        return ResponseEntity.ok("Goodbye");
    }
    

}
