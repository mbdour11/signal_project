package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.*;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    private final Map<String, AlertStrategy> strategies = new HashMap<>();
    private final Map<String, AlertFactory> factories = new HashMap<>();

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;

        // Initialize strategies and factories
        strategies.put("BloodPressureSystolic", new BloodPressureStrategy());
        strategies.put("BloodPressureDiastolic", new BloodPressureStrategy());
        strategies.put("SpO2", new OxygenSaturationStrategy());
        strategies.put("ECG", new HeartRateStrategy()); // Optional

        factories.put("BloodPressureSystolic", new BloodPressureAlertFactory());
        factories.put("BloodPressureDiastolic", new BloodPressureAlertFactory());
        factories.put("SpO2", new BloodOxygenAlertFactory());

    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        for (PatientRecord record : patient.getRecords(Long.MIN_VALUE, Long.MAX_VALUE)) {
            String type = record.getRecordType();
            if (strategies.containsKey(type)) {
                AlertStrategy strategy = strategies.get(type);
                if (strategy.checkAlert(record)) {
                    AlertFactory factory = factories.get(type);
                    Alert alert = factory.createAlert(
                            String.valueOf(record.getPatientId()),
                            "Abnormal " + type + ": " + record.getMeasurementValue(),
                            record.getTimestamp()
                    );
                    alert.display();
                    triggerAlert(alert);
                }
            }
        }
        checkRapidSpo2Drop(patient);
        checkRapidSpo2Drop(patient);
        checkHypotensiveHypoxemia(patient);
        checkECGAnomaly(patient);
    }

    private void checkRapidSpo2Drop(Patient patient) {
        List<PatientRecord> spo2Records = new ArrayList<>();

        for (PatientRecord record : patient.getRecords(Long.MIN_VALUE, Long.MAX_VALUE)) {
            if (record.getRecordType().equals("SpO2")) {
                spo2Records.add(record);
            }
        }

        // Sort by timestamp
        spo2Records.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        for (int i = 0; i < spo2Records.size(); i++) {
            PatientRecord start = spo2Records.get(i);
            long startTime = start.getTimestamp();
            double startValue = start.getMeasurementValue();

            for (int j = i + 1; j < spo2Records.size(); j++) {
                PatientRecord end = spo2Records.get(j);
                long endTime = end.getTimestamp();

                if (endTime - startTime > 10 * 60 * 1000) break; // outside 10 min

                double endValue = end.getMeasurementValue();
                if (startValue - endValue >= 5.0) {
                    String patientId = String.valueOf(start.getPatientId());
                    String message = "Rapid SpO2 Drop: from " + startValue + "% to " + endValue + "%";
                    long timestamp = endTime;

                    Alert alert = factories.get("SpO2").createAlert(patientId, message, timestamp);
                    triggerAlert(alert);
                    triggerAlert(alert);
                    break; // alert triggered, skip to next i
                }
            }
        }
    }

    private void checkHypotensiveHypoxemia(Patient patient) {
        List<PatientRecord> records = patient.getRecords(Long.MIN_VALUE, Long.MAX_VALUE);
        records.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        Double latestLowSystolic = null;
        Long systolicTime = null;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("BloodPressureSystolic") &&
                    record.getMeasurementValue() < 90) {
                latestLowSystolic = record.getMeasurementValue();
                systolicTime = record.getTimestamp();
            }

            if (record.getRecordType().equals("SpO2") &&
                    record.getMeasurementValue() < 92 &&
                    latestLowSystolic != null &&
                    Math.abs(record.getTimestamp() - systolicTime) <= 60_000) { // within 1 minute
                String patientId = String.valueOf(record.getPatientId());
                String message = " Hypotensive Hypoxemia Detected (BP: " + latestLowSystolic +
                        ", SpO2: " + record.getMeasurementValue() + ")";
                long timestamp = record.getTimestamp();

                Alert alert = factories.get("SpO2").createAlert(patientId, message, timestamp);
                triggerAlert(alert);
                latestLowSystolic = null; // prevent duplicate alert
                triggerAlert(alert);
            }
        }
    }

    private void checkECGAnomaly(Patient patient) {
        List<PatientRecord> ecgRecords = new ArrayList<>();

        for (PatientRecord record : patient.getRecords(Long.MIN_VALUE, Long.MAX_VALUE)) {
            if (record.getRecordType().equals("ECG")) {
                ecgRecords.add(record);
            }
        }

        ecgRecords.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        for (int i = 0; i < ecgRecords.size(); i++) {
            PatientRecord current = ecgRecords.get(i);
            long currentTime = current.getTimestamp();

            // Collect all records within 5 minutes before this one
            List<Double> windowValues = new ArrayList<>();
            for (int j = i - 1; j >= 0; j--) {
                PatientRecord past = ecgRecords.get(j);
                if (currentTime - past.getTimestamp() <= 5 * 60 * 1000) {
                    windowValues.add(past.getMeasurementValue());
                } else {
                    break;
                }
            }

            if (windowValues.size() >= 3) { // only if there's enough context
                double sum = 0;
                for (double v : windowValues) sum += v;
                double avg = sum / windowValues.size();

                if (current.getMeasurementValue() >= 3 * avg) {
                    String patientId = String.valueOf(current.getPatientId());
                    String message = "ECG Anomaly Detected: " + current.getMeasurementValue() + " (Avg: " + avg + ")";
                    long timestamp = currentTime;

                    Alert alert = factories.get("ECG").createAlert(patientId, message, timestamp);
                    triggerAlert(alert);
                }
            }
        }
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
    }
}
