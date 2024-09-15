package com.example.employeeonboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.employeeonboarding", "com.example.employeeonboarding.config"})
public class EmployeeOnboardingApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeOnboardingApplication.class, args);
    }
}