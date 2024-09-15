package com.example.employeeonboarding.dto;

import java.time.LocalDateTime;

public class EmployeeDTO {
    private String employeeId;
    private String employeeName;
    private String email;
    private LocalDateTime onboardingStartDate;
    private String onboardingStatus;

    // Constructors
    public EmployeeDTO() {}

    public EmployeeDTO(String employeeId, String employeeName, String email, LocalDateTime onboardingStartDate, String onboardingStatus) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.onboardingStartDate = onboardingStartDate;
        this.onboardingStatus = onboardingStatus;
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getOnboardingStartDate() {
        return onboardingStartDate;
    }

    public void setOnboardingStartDate(LocalDateTime onboardingStartDate) {
        this.onboardingStartDate = onboardingStartDate;
    }

    public String getOnboardingStatus() {
        return onboardingStatus;
    }

    public void setOnboardingStatus(String onboardingStatus) {
        this.onboardingStatus = onboardingStatus;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", email='" + email + '\'' +
                ", onboardingStartDate=" + onboardingStartDate +
                ", onboardingStatus='" + onboardingStatus + '\'' +
                '}';
    }
}