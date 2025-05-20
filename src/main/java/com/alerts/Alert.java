package com.alerts;

public abstract class Alert {
    protected String patientId;
    protected String condition;
    protected long timestamp;

    public Alert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    public String getPatientId() { return patientId; }
    public String getCondition() { return condition; }
    public long getTimestamp() { return timestamp; }

    public abstract void display(); // force subclasses to implement something

    public abstract void trigger();
}

