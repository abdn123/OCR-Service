package com.example.Backend_Project.dto;

import java.util.Date;

public class LoginResponse {
    private String token;

    private Date setExpiresIn;

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

    
}
