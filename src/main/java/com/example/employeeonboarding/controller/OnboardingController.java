package com.example.employeeonboarding.controller;

import com.example.employeeonboarding.dto.*;
import com.example.employeeonboarding.service.NotificationService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/onboarding")
public class OnboardingController {

    private static final Logger logger = LoggerFactory.getLogger(OnboardingController.class);

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private HistoryService historyService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/start")
    public ResponseEntity<String> startOnboardingProcess(@RequestBody Map<String, Object> payload) {
        logger.info("Starting onboarding process with payload: {}", payload);

        String employeeName = (String) payload.get("employeeName");
        String employeeId = (String) payload.get("employeeId");
        String email = (String) payload.get("email");

        if (employeeName == null || employeeId == null || email == null) {
            logger.error("Missing required fields in payload");
            return ResponseEntity.badRequest().body("Employee name, ID, and email are required");
        }

        OnboardingProcessDto processDto = new OnboardingProcessDto();
        processDto.setEmployeeId(employeeId);
        processDto.setEmployeeName(employeeName);
        processDto.setEmail(email);
        processDto.setCurrentStatus("Process Started");

        Map<String, Object> variables = new HashMap<>();
        variables.put("processData", processDto);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("employee-onboarding", employeeId, variables);
        
        String processInstanceId = processInstance.getId();
        processDto.setProcessInstanceId(processInstanceId);
        
        runtimeService.setVariable(processInstanceId, "processData", processDto);

        logger.info("Onboarding process started with ID: {}", processInstanceId);

        notificationService.sendWelcomeMessage(employeeId, email);

        return ResponseEntity.ok("Onboarding process started with ID: " + processInstanceId);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getActiveTasks() {
        List<Task> tasks = taskService.createTaskQuery().active().list();
        List<TaskDto> taskDtos = tasks.stream()
                .map(task -> new TaskDto(task.getId(), task.getName(), task.getAssignee()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDtos);
    }

    @PostMapping("/tasks/{taskId}/claim")
    public ResponseEntity<?> claimTask(@PathVariable String taskId) {
        logger.info("Attempting to claim task: {}", taskId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        
        if (task == null) {
            logger.warn("Task not found: {}", taskId);
            return ResponseEntity.notFound().build();
        }

        // Check if the task is already assigned to someone else
        if (task.getAssignee() != null && !task.getAssignee().equals(currentUserId)) {
            logger.warn("Task {} is already assigned to another user", taskId);
            return ResponseEntity.badRequest().body("Task is already assigned to another user");
        }

        // Check if the current user is allowed to claim this task
        boolean canClaim = taskService.createTaskQuery()
                .taskId(taskId)
                .taskCandidateUser(currentUserId)
                .count() > 0;

        if (!canClaim) {
            logger.warn("User {} is not authorized to claim task {}", currentUserId, taskId);
            return ResponseEntity.status(403).body("You are not authorized to claim this task");
        }

        try {
            taskService.claim(taskId, currentUserId);
            logger.info("Task {} claimed by user {}", taskId, currentUserId);
        } catch (Exception e) {
            logger.error("Error claiming task: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error claiming task: " + e.getMessage());
        }

        OnboardingProcessDto processDto = (OnboardingProcessDto) taskService.getVariable(taskId, "processData");

        Map<String, Object> response = new HashMap<>();
        response.put("taskId", taskId);
        response.put("taskName", task.getName());
        response.put("assignee", currentUserId);

        switch (task.getName()) {
            case "Onboarding Checklist":
                response.put("checklistData", processDto.getChecklistData());
                break;
            case "Training Enrollment":
                response.put("trainingData", processDto.getTrainingData());
                break;
            case "Onboarding Feedback":
                response.put("feedbackData", processDto.getFeedbackData());
                break;
            default:
                response.put("message", "Task claimed successfully");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/tasks/{taskId}/complete")
    public ResponseEntity<String> completeTask(@PathVariable String taskId, @RequestBody Map<String, Object> payload) {
        System.out.println("Attempting to complete task: " + taskId + " with payload: " + payload);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        System.out.println("Current user ID: " + currentUserId);
        System.out.println("User roles: " + authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", ")));

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        
        if (task == null) {
            System.out.println("Task not found: " + taskId);
            return ResponseEntity.notFound().build();
        }

        System.out.println("Task details - ID: " + task.getId() + ", Name: " + task.getName() + ", Assignee: " + task.getAssignee());

        // Check if the current user is assigned to the task
        boolean isAssigned = task.getAssignee() != null && task.getAssignee().equals(currentUserId);
        System.out.println("Is user assigned to task? " + isAssigned);

        // Check if the current user is a candidate for the task
        boolean isCandidate = taskService.createTaskQuery().taskId(taskId).taskCandidateUser(currentUserId).count() > 0;
        System.out.println("Is user a candidate for task? " + isCandidate);

        boolean canComplete = isAssigned || isCandidate;

        if (!canComplete) {
            System.out.println("User " + currentUserId + " is not authorized to complete task " + taskId);
            return ResponseEntity.status(403).body("You are not authorized to complete this task");
        }

        OnboardingProcessDto processDto = (OnboardingProcessDto) taskService.getVariable(taskId, "processData");
        System.out.println("Process DTO: " + processDto);

        updateProcessDtoBasedOnTask(task, processDto, payload);

        taskService.setVariable(taskId, "processData", processDto);

        try {
            taskService.complete(taskId, payload);
            System.out.println("Task " + taskId + " completed successfully by user " + currentUserId);
        } catch (Exception e) {
            System.out.println("Error completing task: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error completing task: " + e.getMessage());
        }

        return ResponseEntity.ok("Task completed successfully");
    }
    
    @GetMapping("/tasks/group/{groupId}")
    public ResponseEntity<List<TaskDto>> getActiveTasksForGroup(@PathVariable String groupId) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskCandidateGroup(groupId)
                .active()
                .list();
        List<TaskDto> taskDtos = tasks.stream()
                .map(task -> new TaskDto(task.getId(), task.getName(), task.getAssignee()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDtos);
    }

    @GetMapping("/process/{processInstanceId}")
    public ResponseEntity<OnboardingProcessDto> getProcessStatus(@PathVariable String processInstanceId) {
        OnboardingProcessDto processDto = (OnboardingProcessDto) runtimeService.getVariable(processInstanceId, "processData");
        
        if (processDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(processDto);
    }
    
    @GetMapping("/tasks/user/{userId}")
    public ResponseEntity<List<TaskDto>> getActiveTasksForUser(@PathVariable String userId) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(userId)
                .active()
                .list();
        
        List<TaskDto> taskDtos = tasks.stream()
                .map(task -> new TaskDto(task.getId(), task.getName(), task.getAssignee()))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(taskDtos);
    }
    
    @GetMapping("/processes/completed")
    public ResponseEntity<List<ProcessInstanceDto>> getCompletedProcessInstances() {
        List<HistoricProcessInstance> completedProcesses = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey("employee-onboarding")
                .finished()
                .list();
        
        List<ProcessInstanceDto> processInstanceDtos = completedProcesses.stream()
                .map(process -> {
                    HistoricVariableInstance processDataVar = historyService.createHistoricVariableInstanceQuery()
                            .processInstanceId(process.getId())
                            .variableName("processData")
                            .singleResult();
                    OnboardingProcessDto processData = (OnboardingProcessDto) processDataVar.getValue();
                    return new ProcessInstanceDto(
                        process.getId(),
                        process.getBusinessKey(),
                        processData.getEmployeeName(),
                        processData.getCurrentStatus(),
                        process.getEndTime()
                    );
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(processInstanceDtos);
    }

    private void updateProcessDtoBasedOnTask(Task task, OnboardingProcessDto processDto, Map<String, Object> payload) {
    	System.out.println("Updating process DTO based on task: " + task.getName());
        switch (task.getName()) {
            case "Onboarding Checklist":
                OnboardingChecklistDto checklistDto = mapToChecklistDto(payload);
                processDto.setChecklistData(checklistDto);
                processDto.setCurrentStatus("Checklist Completed");
                break;
            case "Training Enrollment":
                TrainingEnrollmentDto trainingDto = mapToTrainingDto(payload);
                processDto.setTrainingData(trainingDto);
                processDto.setCurrentStatus("Training Enrolled");
                break;
            case "Onboarding Feedback":
                OnboardingFeedbackDto feedbackDto = mapToFeedbackDto(payload);
                processDto.setFeedbackData(feedbackDto);
                processDto.setCurrentStatus("Feedback Provided");
                break;
            default:
                logger.warn("Unknown task name: {}", task.getName());
        }
    }

    private OnboardingChecklistDto mapToChecklistDto(Map<String, Object> payload) {
        OnboardingChecklistDto dto = new OnboardingChecklistDto();
        dto.setEmployeeId((String) payload.get("employeeId"));
        dto.setIdCardIssued((Boolean) payload.get("idCardIssued"));
        dto.setWorkspaceAssigned((Boolean) payload.get("workspaceAssigned"));
        dto.setEquipmentProvided((Boolean) payload.get("equipmentProvided"));
        dto.setCompletedOrientations((List<String>) payload.get("completedOrientations"));
        dto.setAdditionalNotes((String) payload.get("additionalNotes"));
        return dto;
    }

    private TrainingEnrollmentDto mapToTrainingDto(Map<String, Object> payload) {
        TrainingEnrollmentDto dto = new TrainingEnrollmentDto();
        dto.setEmployeeId((String) payload.get("employeeId"));
        dto.setSelectedTrainings((List<String>) payload.get("selectedTrainings"));
        dto.setPreferredStartDate((String) payload.get("preferredStartDate"));
        dto.setAcknowledgedTrainingPolicy((Boolean) payload.get("acknowledgedTrainingPolicy"));
        return dto;
    }

    private OnboardingFeedbackDto mapToFeedbackDto(Map<String, Object> payload) {
        OnboardingFeedbackDto dto = new OnboardingFeedbackDto();
        dto.setEmployeeId((String) payload.get("employeeId"));
        dto.setOverallSatisfaction((Integer) payload.get("overallSatisfaction"));
        dto.setOrientationQuality((Integer) payload.get("orientationQuality"));
        dto.setTrainingEffectiveness((Integer) payload.get("trainingEffectiveness"));
        dto.setPositiveAspects((String) payload.get("positiveAspects"));
        dto.setAreasForImprovement((String) payload.get("areasForImprovement"));
        dto.setAdditionalComments((String) payload.get("additionalComments"));
        return dto;
    }
}