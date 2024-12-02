package org.firstinspires.ftc.teamcode.baseClasses.Components;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.HardwareSingle;

public abstract class RoboticsMotor {

    protected DcMotor motor;

    protected MotorParameters params;

    public RoboticsMotor(MotorParameters params) {
        this.params = params;
        motor = HardwareSingle.hardwareMap.get(DcMotor.class, params.name());

        motor.setDirection(params.motorDirection());

        //TODO: make parameter
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        resetEncoder();

        //TODO: make parameter
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void resetEncoder() { motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); }
}

