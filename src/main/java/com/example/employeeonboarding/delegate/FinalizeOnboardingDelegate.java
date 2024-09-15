package com.example.employeeonboarding.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class FinalizeOnboardingDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Logic to finalize onboarding
        System.out.println("Finalizing onboarding for: " + execution.getVariable("employeeName"));
    }
}