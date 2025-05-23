package com.alerts;

public abstract class AlertDecorator extends Alert {
    protected Alert decoratedAlert;

    public AlertDecorator(Alert decoratedAlert){

        super(decoratedAlert.getPatientId(), decoratedAlert.getCondition(), decoratedAlert.getTimestamp());
        this.decoratedAlert = decoratedAlert;
    }
    @Override
    public void display() {
        decoratedAlert.display();
    }
    @Override
    public void trigger() {
        decoratedAlert.trigger();
    }
}

