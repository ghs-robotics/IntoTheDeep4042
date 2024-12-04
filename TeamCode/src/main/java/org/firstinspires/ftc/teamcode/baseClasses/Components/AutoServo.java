package org.firstinspires.ftc.teamcode.baseClasses.Components;

public class AutoServo extends RoboticsServo implements AutoComponent {

    public AutoServo(ServoParameters params) { super(params); }

    @Override
    public boolean moveTo(double pos) {
        if (params.isContinuousRotation()) return false;

        servo.setPosition(pos);
        return true;
    }

    @Override
    public boolean moveToStored(String key) {
        if (params.isContinuousRotation() || !params.getPositions().containsValue(key)) return false;

        servo.setPosition(params.getPositions().get(key));
        return true;
    }
}
