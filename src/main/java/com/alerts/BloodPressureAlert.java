package com.alerts;

public class BloodPressureAlert extends Alert {
    private String patientId;
    private String condition;
    private long timestamp;
    private String message;

    public BloodPressureAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    @Override
    public void trigger() {
        System.out.println("ALERT for " + patientId + ": " + condition + " at " + timestamp);
    }

    @Override
    public void display() {
        System.out.println("Blood Oxygen Alert - Patient ID: " + patientId);
        System.out.println("Message: " + message);
        System.out.println("Timestamp: " + timestamp);
    }

}