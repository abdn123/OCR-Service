package com.example.backend_project.dto;

public class ResetPasswordDto {

    String username;

    String oldPassword;

    String newPassword1;
    
    String newPassword2;

    public String getOldPassword() {
        return oldPassword;
    }
    public String getNewPassword1() {
        return newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public String getUsername() {
        return username;
    }
}
