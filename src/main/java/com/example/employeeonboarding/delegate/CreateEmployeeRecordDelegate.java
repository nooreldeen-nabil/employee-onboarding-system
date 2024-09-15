package com.example.employeeonboarding.delegate;

import com.example.employeeonboarding.dto.OnboardingProcessDto;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class CreateEmployeeRecordDelegate implements JavaDelegate {

    @Autowired
    private IdentityService identityService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${hr.system.api.url}")
    private String hrSystemApiUrl;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        OnboardingProcessDto processDto = (OnboardingProcessDto) execution.getVariable("processData");

        if (processDto == null) {
            throw new IllegalStateException("OnboardingProcessDto not found in process variables");
        }

        String employeeName = processDto.getEmployeeName();
        String employeeId = processDto.getEmployeeId();
        String email = processDto.getEmail();

        // Create user in Camunda
        if (identityService.createUserQuery().userId(employeeId).singleResult() == null) {
            User newEmployee = identityService.newUser(employeeId);
            newEmployee.setFirstName(employeeName.split(" ")[0]);
            newEmployee.setLastName(employeeName.split(" ").length > 1 ? employeeName.split(" ")[1] : "");
            newEmployee.setEmail(email);
            newEmployee.setPassword("000000"); // Set a default password
            identityService.saveUser(newEmployee);
            System.out.println("Created new employee in Camunda: " + employeeId);
            System.out.println("Password:  000000");
        }

        // Set the employeeId as a process variable to be used for task assignment
        execution.setVariable("employeeId", employeeId);

        // Integrate with .NET Core HR System (simulated with JSONPlaceholder)
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> employeeData = new HashMap<>();
            employeeData.put("name", employeeName);
            employeeData.put("email", email);
            employeeData.put("employeeId", employeeId);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(employeeData, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(hrSystemApiUrl + "/users", request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Successfully created employee record in HR system for: " + employeeName);
                System.out.println("HR System Response: " + response.getBody());
                processDto.setCurrentStatus("Employee Record Created");
            } else {
                System.out.println("Unexpected response from HR system. Status code: " + response.getStatusCodeValue());
                processDto.setCurrentStatus("Error Creating Employee Record");
            }
        } catch (Exception e) {
            System.err.println("Error creating employee record in HR system: " + e.getMessage());
            processDto.setCurrentStatus("Error Creating Employee Record");
            // Log the error, but don't throw an exception to allow the process to continue
        }

        // Update the processDto in the process variables
        execution.setVariable("processData", processDto);

        // Set additional process variables if needed
        execution.setVariable("employeeRecordCreated", true);
    }
}