package com.example.employeeonboarding.dto;

import java.util.Date;

public class ProcessInstanceDto {
    private String id;
    private String businessKey;
    private String employeeName;
    private String currentStatus;
    private Date endTime;

    public ProcessInstanceDto(String id, String businessKey, String employeeName, String currentStatus, Date endTime) {
        this.id = id;
        this.businessKey = businessKey;
        this.employeeName = employeeName;
        this.currentStatus = currentStatus;
        this.endTime = endTime;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}