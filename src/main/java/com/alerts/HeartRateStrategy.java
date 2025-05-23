package com.alerts;
import com.data_management.PatientRecord;

public class HeartRateStrategy implements AlertStrategy {

    private static final int HEART_RATE_LOW = 50;
    private static final int HEART_RATE_HIGH = 100;

    @Override
    public boolean checkAlert(PatientRecord record) {
        return (record.getMeasurementValue() < HEART_RATE_LOW || record.getMeasurementValue() > HEART_RATE_HIGH);
    }
}
