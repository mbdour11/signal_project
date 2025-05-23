package com.alerts;
import com.data_management.PatientRecord;

public class OxygenSaturationStrategy implements AlertStrategy {

    private static final int OXYGEN_THRESHOLD = 92;

    @Override
    public boolean checkAlert(PatientRecord record) {
        return (record.getMeasurementValue() < OXYGEN_THRESHOLD);
    }
}
