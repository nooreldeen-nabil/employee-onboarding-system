package com.example.employeeonboarding.dto;

import java.io.Serializable;
import java.util.List;

public class OnboardingChecklistDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String employeeId;
    private boolean idCardIssued;
    private boolean workspaceAssigned;
    private boolean equipmentProvided;
    private List<String> completedOrientations;
    private String additionalNotes;

    // Default constructor
    public OnboardingChecklistDto() {}

    // Constructor with all fields
    public OnboardingChecklistDto(String employeeId, boolean idCardIssued, boolean workspaceAssigned,
                                  boolean equipmentProvided, List<String> completedOrientations,
                                  String additionalNotes) {
        this.employeeId = employeeId;
        this.idCardIssued = idCardIssued;
        this.workspaceAssigned = workspaceAssigned;
        this.equipmentProvided = equipmentProvided;
        this.completedOrientations = completedOrientations;
        this.additionalNotes = additionalNotes;
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isIdCardIssued() {
        return idCardIssued;
    }

    public void setIdCardIssued(boolean idCardIssued) {
        this.idCardIssued = idCardIssued;
    }

    public boolean isWorkspaceAssigned() {
        return workspaceAssigned;
    }

    public void setWorkspaceAssigned(boolean workspaceAssigned) {
        this.workspaceAssigned = workspaceAssigned;
    }

    public boolean isEquipmentProvided() {
        return equipmentProvided;
    }

    public void setEquipmentProvided(boolean equipmentProvided) {
        this.equipmentProvided = equipmentProvided;
    }

    public List<String> getCompletedOrientations() {
        return completedOrientations;
    }

    public void setCompletedOrientations(List<String> completedOrientations) {
        this.completedOrientations = completedOrientations;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    @Override
    public String toString() {
        return "OnboardingChecklistDto{" +
                "employeeId='" + employeeId + '\'' +
                ", idCardIssued=" + idCardIssued +
                ", workspaceAssigned=" + workspaceAssigned +
                ", equipmentProvided=" + equipmentProvided +
                ", completedOrientations=" + completedOrientations +
                ", additionalNotes='" + additionalNotes + '\'' +
                '}';
    }
}