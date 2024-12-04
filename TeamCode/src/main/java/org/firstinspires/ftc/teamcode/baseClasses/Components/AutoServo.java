package org.firstinspires.ftc.teamcode.baseClasses.Components;

public class AutoServo extends RoboticsServo implements AutoComponent {

    public AutoServo(ServoParameters params) { super(params); }

    @Override
    public void moveTo(double pos) {
        if (params.isContinuousRotation()) return;

        servo.setPosition(pos);
    }

    @Override
    public void moveToStored(String key) {
        if (params.isContinuousRotation() || !params.getPositions().containsValue(key)) return;

        servo.setPosition(params.getPositions().get(key));
    }
}
