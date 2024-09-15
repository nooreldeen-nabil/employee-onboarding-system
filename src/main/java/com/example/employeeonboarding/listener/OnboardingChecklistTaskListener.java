package com.example.employeeonboarding.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component
public class OnboardingChecklistTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        // Logic for handling onboarding checklist task events
        System.out.println("Onboarding checklist task event: " + delegateTask.getEventName());
    }
}