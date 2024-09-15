package com.example.employeeonboarding.dto;

import java.io.Serializable;

public class OnboardingProcessDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String processInstanceId;
    private String employeeId;
    private String employeeName;
    private String email;
    private String currentStatus;
    private OnboardingChecklistDto checklistData;
    private TrainingEnrollmentDto trainingData;
    private OnboardingFeedbackDto feedbackData;

    // Default constructor
    public OnboardingProcessDto() {}

    // Constructor with all fields
    public OnboardingProcessDto(String processInstanceId, String employeeId, String employeeName,
                                String email, String currentStatus, OnboardingChecklistDto checklistData,
                                TrainingEnrollmentDto trainingData, OnboardingFeedbackDto feedbackData) {
        this.processInstanceId = processInstanceId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.currentStatus = currentStatus;
        this.checklistData = checklistData;
        this.trainingData = trainingData;
        this.feedbackData = feedbackData;
    }

    // Getters and Setters
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

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

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public OnboardingChecklistDto getChecklistData() {
        return checklistData;
    }

    public void setChecklistData(OnboardingChecklistDto checklistData) {
        this.checklistData = checklistData;
    }

    public TrainingEnrollmentDto getTrainingData() {
        return trainingData;
    }

    public void setTrainingData(TrainingEnrollmentDto trainingData) {
        this.trainingData = trainingData;
    }

    public OnboardingFeedbackDto getFeedbackData() {
        return feedbackData;
    }

    public void setFeedbackData(OnboardingFeedbackDto feedbackData) {
        this.feedbackData = feedbackData;
    }

    @Override
    public String toString() {
        return "OnboardingProcessDto{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", email='" + email + '\'' +
                ", currentStatus='" + currentStatus + '\'' +
                ", checklistData=" + checklistData +
                ", trainingData=" + trainingData +
                ", feedbackData=" + feedbackData +
                '}';
    }
}