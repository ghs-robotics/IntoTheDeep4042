package org.firstinspires.ftc.teamcode.opmodes_teleop.input;

public class Button {
    private boolean lastState;
    private boolean currentState;

    public void update(boolean state){
        lastState = currentState;
        currentState = state;
    }

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
