package com.alerts;

public class BloodOxygenAlert extends Alert {
    private String patientId;
    private String message;
    private long timestamp;

    public BloodOxygenAlert(String patientId, String message, long timestamp) {
        super(patientId, message, timestamp);
        this.patientId = patientId;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public void trigger() {
        System.out.println("BP ALERT for patient " + patientId + ": " + message + " at " + timestamp);
    }
    @Override
    public void display() {
        System.out.println("Blood Oxygen Alert - Patient ID: " + patientId);
        System.out.println("Message: " + message);
        System.out.println("Timestamp: " + timestamp);
    }

}
