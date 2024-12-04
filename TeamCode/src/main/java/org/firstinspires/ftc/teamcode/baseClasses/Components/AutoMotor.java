package org.firstinspires.ftc.teamcode.baseClasses.Components;

public class AutoMotor extends RoboticsMotor implements AutoComponent {

    protected boolean autoMoving = false;

    public AutoMotor(MotorParameters params) {
        super(params);
    }

    @Override
    public boolean moveTo(double targetPos) {
        //TODO: Figure out how to seperate initializing variables and calling move to every frame
        double error = motor.getCurrentPosition() - targetPos;

        if (Math.abs(error) > 15/*threshold variable*/) motor.setPower(-Math.signum(error));
        else {
            motor.setPower(0);
            return true;
        }

        return false;
    }

    public boolean moveToPID(double targetPos) {
        //TODO: Implement
        return false;
    }

    @Override
    public boolean moveToStored(String key) {
        if (!params.getPositions().containsValue(key)) return false;

        return moveTo(params.getPositions().get(key));
    }

    public boolean moveToStoredPID(String key) {
        if (!params.getPositions().containsValue(key)) return false;

        return moveToPID(params.getPositions().get(key));
    }
}
