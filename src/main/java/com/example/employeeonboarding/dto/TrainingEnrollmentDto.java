package com.example.employeeonboarding.dto;

import java.io.Serializable;
import java.util.List;

public class TrainingEnrollmentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String employeeId;
    private List<String> selectedTrainings;
    private String preferredStartDate;
    private boolean acknowledgedTrainingPolicy;

    // Default constructor
    public TrainingEnrollmentDto() {}

    // Constructor with all fields
    public TrainingEnrollmentDto(String employeeId, List<String> selectedTrainings,
                                 String preferredStartDate, boolean acknowledgedTrainingPolicy) {
        this.employeeId = employeeId;
        this.selectedTrainings = selectedTrainings;
        this.preferredStartDate = preferredStartDate;
        this.acknowledgedTrainingPolicy = acknowledgedTrainingPolicy;
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public List<String> getSelectedTrainings() {
        return selectedTrainings;
    }

    public void setSelectedTrainings(List<String> selectedTrainings) {
        this.selectedTrainings = selectedTrainings;
    }

    public String getPreferredStartDate() {
        return preferredStartDate;
    }

    public void setPreferredStartDate(String preferredStartDate) {
        this.preferredStartDate = preferredStartDate;
    }

    public boolean isAcknowledgedTrainingPolicy() {
        return acknowledgedTrainingPolicy;
    }

    public void setAcknowledgedTrainingPolicy(boolean acknowledgedTrainingPolicy) {
        this.acknowledgedTrainingPolicy = acknowledgedTrainingPolicy;
    }

    @Override
    public String toString() {
        return "TrainingEnrollmentDto{" +
                "employeeId='" + employeeId + '\'' +
                ", selectedTrainings=" + selectedTrainings +
                ", preferredStartDate='" + preferredStartDate + '\'' +
                ", acknowledgedTrainingPolicy=" + acknowledgedTrainingPolicy +
                '}';
    }
}