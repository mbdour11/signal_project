package com.alerts;

public class BloodPressureAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String message, long timestamp) {
        return new BloodPressureAlert(patientId, message, timestamp);
    }
}
