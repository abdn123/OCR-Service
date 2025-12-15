package com.example.Backend_Project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping    
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("hello from Hello API");
    }

    @GetMapping("/say-goodbye")
    public ResponseEntity<String> sayGoodbye() {
        return ResponseEntity.ok("Goodbye!");
    }
    
}
