package com.example.Backend_Project.dto;

import java.util.Date;

import com.example.Backend_Project.enums.UserRole;

public class LoginResponse {

    private String invalidCredentialsError;
    
    private String token;
    
    private Date setExpiresIn;

    private UserRole role;
    
    public LoginResponse() {}
    
    public LoginResponse(String err) {
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
