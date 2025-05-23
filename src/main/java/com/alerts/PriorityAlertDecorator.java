package com.alerts;

public class PriorityAlertDecorator extends AlertDecorator{
    private String priority;

    public PriorityAlertDecorator(Alert decoratedAlert, String priority) {
        super(decoratedAlert);
        this.priority = priority;

    }

    @Override
    public void display() {
        decoratedAlert.display();
        System.out.println("Priority: " + priority);
    }
    @Override
    public void trigger() {
        decoratedAlert.trigger();
        System.out.println("!!!Priority: " + priority + " !!!");
    }
}
