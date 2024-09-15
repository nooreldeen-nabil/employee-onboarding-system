package com.example.employeeonboarding.service;

import com.example.employeeonboarding.dto.EmployeeDTO;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class EmployeeService {
    private List<EmployeeDTO> employees = new ArrayList<>();

    public EmployeeDTO addEmployee(String employeeId, String employeeName, String email) {
        EmployeeDTO newEmployee = new EmployeeDTO(employeeId, employeeName, email, LocalDateTime.now(), "In Progress");
        employees.add(newEmployee);
        return newEmployee;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public Optional<EmployeeDTO> getEmployeeById(String employeeId) {
        return employees.stream()
                .filter(e -> e.getEmployeeId().equals(employeeId))
                .findFirst();
    }

    public void updateEmployeeStatus(String employeeId, String status) {
        getEmployeeById(employeeId).ifPresent(e -> e.setOnboardingStatus(status));
    }
}