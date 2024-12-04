package org.firstinspires.ftc.teamcode.baseClasses.Components;

public class AutoMotor extends RoboticsMotor implements AutoComponent {

    public AutoMotor(MotorParameters params) {
        super(params);
    }

    @Override
    public void moveTo(double pos) {
        //TODO: Consider PID or seperate PID method
    }

    @Override
    public void moveToStored(String key) {
        //TODO: Consider PID or seperate PID method
        if (!params.getPositions().containsValue(key)) return;

        moveTo(params.getPositions().get(key));
    }
}
