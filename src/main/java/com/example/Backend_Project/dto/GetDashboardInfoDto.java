package com.example.backend_project.dto;

public class GetDashboardInfoDto {

    long totalUsers;
    long activeUsers;
    long totalDocs;

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public void setActiveUsers(long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public void setTotalDocs(long totalDocs) {
        this.totalDocs = totalDocs;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getActiveUsers() {
        return activeUsers;
    }

    public long getTotalDocs() {
        return totalDocs;
    }
}
