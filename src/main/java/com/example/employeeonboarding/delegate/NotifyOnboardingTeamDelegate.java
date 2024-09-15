package com.example.employeeonboarding.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.example.employeeonboarding.dto.OnboardingProcessDto;

@Component
public class NotifyOnboardingTeamDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Logic to notify onboarding team
    	OnboardingProcessDto processDto = (OnboardingProcessDto) execution.getVariable("processData");
        System.out.println("Notifying onboarding team for: " + processDto.getEmployeeName());
    }
}