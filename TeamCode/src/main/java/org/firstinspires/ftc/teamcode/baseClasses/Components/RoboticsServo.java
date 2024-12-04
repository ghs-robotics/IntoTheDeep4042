package org.firstinspires.ftc.teamcode.baseClasses.Components;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.HardwareSingle;

public abstract class RoboticsServo {

    protected Servo servo;
    protected CRServo crServo;

    protected ServoParameters params;

    public RoboticsServo(ServoParameters params) {
        this.params = params;

        if (params.isContinuousRotation()) {
            crServo = HardwareSingle.hardwareMap.get(CRServo.class, params.getName());
        }
        else servo = HardwareSingle.hardwareMap.get(Servo.class, params.getName());

        servo.getController().pwmEnable();

        servo.setDirection(params.getServoDirection());
    }
}

