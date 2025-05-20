package com.alerts;

public class BloodOxygenAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String message, long timestamp) {
        return new BloodOxygenAlert(patientId, message, timestamp);
    }
}
