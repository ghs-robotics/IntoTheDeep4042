package org.firstinspires.ftc.teamcode.opmodes_teleop.input;

public class Trigger {
    private double value;

    private boolean lastState;
    private boolean currentState;

    public void update(double value){
        this.value = value;

        lastState = currentState;
        currentState = value >= 0.1;
    }

    public double getValue() { return value; }

    public boolean pressing(){
        return currentState;
    }

    public boolean pressed(){
        return lastState != currentState && !lastState;
    }

    public boolean released(){
        return lastState != currentState && lastState;
    }
}
