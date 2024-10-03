package com.example.Backend_Project.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserMessageDto {

    String message;

    String username;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
