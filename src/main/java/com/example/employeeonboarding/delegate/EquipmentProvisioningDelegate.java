package com.example.employeeonboarding.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class EquipmentProvisioningDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Logic to provision equipment
        System.out.println("Provisioning equipment for: " + execution.getVariable("employeeName"));
    }
}
