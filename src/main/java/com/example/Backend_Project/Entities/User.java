package com.example.Backend_Project.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "user_info")
@Entity
public class User {

    @Column(name="username",unique=true, nullable=false)
    private String username;

    @Column(name="email", unique=true, length=100, nullable=false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;
    
    @Column(name="active", nullable = false)
    private boolean active;



    public User(String username, String email, String password, boolean active) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
