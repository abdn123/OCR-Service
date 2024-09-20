package com.example.Backend_Project.Entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Table
@Entity(name = "user_info")
@EqualsAndHashCode(of = "user_ID")
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @Column(name="user_ID",unique = true, nullable = false)
    private Long user_ID;

    @Column(name="username",unique=true, nullable=false)
    private String username;

    @Column(unique=true, length=100, nullable=false)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private boolean active;

    @Column
    private String role;

    public User(){}

    public User(String username, String email, String password, boolean active, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
        // if (this.role == UserRole.ADMIN) {
        // return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        // }
        // return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
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

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}