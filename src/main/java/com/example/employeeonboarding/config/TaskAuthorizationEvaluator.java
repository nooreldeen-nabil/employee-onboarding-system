package com.example.employeeonboarding.config;

import org.camunda.bpm.engine.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class TaskAuthorizationEvaluator {

    private final TaskService taskService;

    public TaskAuthorizationEvaluator(TaskService taskService) {
        this.taskService = taskService;
    }

    public boolean isAssignedOrCandidate(Authentication authentication, String taskId) {
        if (taskId == null) {
            System.out.println("Task ID is null, authorization check failed");
            return false;
        }

        String username = authentication.getName();
        System.out.println("Checking task authorization for user: " + username + " and task: " + taskId);
        
        boolean isAssigned = taskService.createTaskQuery()
                .taskId(taskId)
                .taskAssignee(username)
                .count() > 0;
        
        boolean isCandidate = taskService.createTaskQuery()
                .taskId(taskId)
                .taskCandidateUser(username)
                .count() > 0;
        
        System.out.println("Is Assigned: " + isAssigned + ", Is Candidate: " + isCandidate);
        return isAssigned || isCandidate;
    }
}