package com.example.backend_project.dto;

public class GetUsersDto {
    
    long totalUsers;
    long activeUsers;
    
    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }
    public void setActiveUsers(long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getActiveUsers() {
        return activeUsers;
    }
}
