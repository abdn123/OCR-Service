package com.example.backend_project.dto;

import java.util.Date;

import com.example.backend_project.enums.UserRole;

public class LoginResponseDto {

    private String invalidCredentialsError;
    
    private String token;
    
    private Date setExpiresIn;

    private UserRole role;
    
    public LoginResponseDto() {}
    
    public LoginResponseDto(String err) {
        invalidCredentialsError = err;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getSetExpiresIn() {
        return setExpiresIn;
    }

    public void setSetExpiresIn(Date setExpiresIn) {
        this.setExpiresIn = setExpiresIn;
    }

    public String getInvalidCredentialsError() {
        return invalidCredentialsError;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    
}
