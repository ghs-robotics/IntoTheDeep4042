package org.firstinspires.ftc.teamcode.baseClasses.Components;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.HardwareSingle;

public abstract class RoboticsMotor {

    protected DcMotor motor;

    protected MotorParameters params;

    public RoboticsMotor(MotorParameters params) {
        this.params = params;

        motor = HardwareSingle.hardwareMap.get(DcMotor.class, params.getName());

        motor.setDirection(params.getMotorDirection());
        motor.setZeroPowerBehavior(params.getZeroPowerBehavior());

        resetEncoder();

        motor.setMode(params.getRunMode());
    }

    public void resetEncoder() { motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); }
}

