package com.alerts;
import com.data_management.PatientRecord;

public class BloodPressureStrategy implements AlertStrategy {

    private static final int SYSTOLIC_THRESHOLD = 140;
    private static final int DIASTOLIC_THRESHOLD = 90;

    @Override
    public boolean checkAlert(PatientRecord record) {
      return (record.getMeasurementValue() > SYSTOLIC_THRESHOLD || record.getMeasurementValue() < DIASTOLIC_THRESHOLD);
    }

}
