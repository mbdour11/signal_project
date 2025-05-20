package com.alerts;

/**
 * Represents a medical alert triggered for a patient based on critical health data.
 * An alert contains the patient's ID, a description of the condition that triggered the alert,
 * and the timestamp at which the alert was generated.
 */
public class Alert {
    private String patientId;
    private String condition;
    private long timestamp;

    /**
     * Constructs an {@code Alert} with the specified patient ID, condition description, and timestamp.
     *
     * @param patientId the unique identifier of the patient
     * @param condition a human-readable description of the alert condition
     * @param timestamp the time at which the alert was triggered, in milliseconds since epoch
     */
    public Alert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    /**
     * Returns the ID of the patient for whom the alert was generated.
     *
     * @return the patient ID
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Returns the condition that triggered the alert.
     *
     * @return the alert condition description
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Returns the timestamp of when the alert was triggered.
     *
     * @return the alert timestamp in milliseconds since epoch
     */
    public long getTimestamp() {
        return timestamp;
    }
}
