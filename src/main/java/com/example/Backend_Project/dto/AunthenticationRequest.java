package com.example.Backend_Project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AunthenticationRequest {

    private String email;
    private String password;
}
