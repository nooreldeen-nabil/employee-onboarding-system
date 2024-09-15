package com.example.employeeonboarding.dto;

public class LoginRequest {
    private String username;
    private String password;

    // Default constructor
    public LoginRequest() {}

    // Constructor with parameters
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString method (be careful not to log passwords in production)
    @Override
    public String toString() {
        return "LoginRequest{" +
               "username='" + username + '\'' +
               ", password='[PROTECTED]'" +
               '}';
    }
}