package com.example.employeeonboarding.dto;

import java.util.Date;
import java.util.Map;

public class TaskDto {
    private String id;
    private String name;
    private String assignee;
    private String owner;
    private Date createTime;
    private Date dueDate;
    private String processInstanceId;
    private String executionId;
    private String processDefinitionId;
    private String taskDefinitionKey;
    private int priority;
    private String description;
    private Map<String, Object> variables;

    // Default constructor
    public TaskDto() {}

    // Constructor with essential fields
    public TaskDto(String id, String name, String assignee) {
        this.id = id;
        this.name = name;
        this.assignee = assignee;
    }

    // Full constructor
    public TaskDto(String id, String name, String assignee, String owner, Date createTime, Date dueDate,
                   String processInstanceId, String executionId, String processDefinitionId,
                   String taskDefinitionKey, int priority, String description, Map<String, Object> variables) {
        this.id = id;
        this.name = name;
        this.assignee = assignee;
        this.owner = owner;
        this.createTime = createTime;
        this.dueDate = dueDate;
        this.processInstanceId = processInstanceId;
        this.executionId = executionId;
        this.processDefinitionId = processDefinitionId;
        this.taskDefinitionKey = taskDefinitionKey;
        this.priority = priority;
        this.description = description;
        this.variables = variables;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", assignee='" + assignee + '\'' +
                ", owner='" + owner + '\'' +
                ", createTime=" + createTime +
                ", dueDate=" + dueDate +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", executionId='" + executionId + '\'' +
                ", processDefinitionId='" + processDefinitionId + '\'' +
                ", taskDefinitionKey='" + taskDefinitionKey + '\'' +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                ", variables=" + variables +
                '}';
    }
}