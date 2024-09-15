package com.example.employeeonboarding.dto;

import java.io.Serializable;

public class OnboardingFeedbackDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String employeeId;
    private int overallSatisfaction;
    private int orientationQuality;
    private int trainingEffectiveness;
    private String positiveAspects;
    private String areasForImprovement;
    private String additionalComments;

    // Default constructor
    public OnboardingFeedbackDto() {}

    // Constructor with all fields
    public OnboardingFeedbackDto(String employeeId, int overallSatisfaction, int orientationQuality, 
                                 int trainingEffectiveness, String positiveAspects, 
                                 String areasForImprovement, String additionalComments) {
        this.employeeId = employeeId;
        this.overallSatisfaction = overallSatisfaction;
        this.orientationQuality = orientationQuality;
        this.trainingEffectiveness = trainingEffectiveness;
        this.positiveAspects = positiveAspects;
        this.areasForImprovement = areasForImprovement;
        this.additionalComments = additionalComments;
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getOverallSatisfaction() {
        return overallSatisfaction;
    }

    public void setOverallSatisfaction(int overallSatisfaction) {
        this.overallSatisfaction = overallSatisfaction;
    }

    public int getOrientationQuality() {
        return orientationQuality;
    }

    public void setOrientationQuality(int orientationQuality) {
        this.orientationQuality = orientationQuality;
    }

    public int getTrainingEffectiveness() {
        return trainingEffectiveness;
    }

    public void setTrainingEffectiveness(int trainingEffectiveness) {
        this.trainingEffectiveness = trainingEffectiveness;
    }

    public String getPositiveAspects() {
        return positiveAspects;
    }

    public void setPositiveAspects(String positiveAspects) {
        this.positiveAspects = positiveAspects;
    }

    public String getAreasForImprovement() {
        return areasForImprovement;
    }

    public void setAreasForImprovement(String areasForImprovement) {
        this.areasForImprovement = areasForImprovement;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    @Override
    public String toString() {
        return "OnboardingFeedbackDto{" +
                "employeeId='" + employeeId + '\'' +
                ", overallSatisfaction=" + overallSatisfaction +
                ", orientationQuality=" + orientationQuality +
                ", trainingEffectiveness=" + trainingEffectiveness +
                ", positiveAspects='" + positiveAspects + '\'' +
                ", areasForImprovement='" + areasForImprovement + '\'' +
                ", additionalComments='" + additionalComments + '\'' +
                '}';
    }
}