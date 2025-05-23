package com.alerts;

public class RepeatedAlertDecorator extends AlertDecorator{

    private int repeatCount;
    private long delayMillis;

    public RepeatedAlertDecorator(Alert decoratedAlert, int repeatCount, long delayMillis) {

        super(decoratedAlert);
        this.repeatCount = repeatCount;
        this.delayMillis = delayMillis;
    }

    @Override
    public void display() {
        decoratedAlert.display();
    }

    @Override
    public void trigger(){
        for (int i = 0; i < repeatCount; i++) {
            decoratedAlert.trigger();
            try{
                Thread.sleep(delayMillis);
            }
            catch(InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("Alert repition interrupted. ");
            }
        }
    }

}
