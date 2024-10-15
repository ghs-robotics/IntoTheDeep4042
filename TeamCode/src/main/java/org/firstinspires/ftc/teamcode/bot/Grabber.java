package org.firstinspires.ftc.teamcode.bot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Grabber {

    private Telemetry telemetry;

    private Servo grabber;

    private static final double grabberStartPos = 0.5;
    private static final double grabberOpenPos = 0.7;
    private static final double grabberClosePos = 0.3;

    public Grabber (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        grabber = hardwareMap.get(Servo.class, "Grabber");

        grabber.setPosition(grabberStartPos);
    }

    public void grabberControllerMovement(boolean open, boolean close) {
        if (open) grabber.setPosition(grabberOpenPos);
        else if (close) grabber.setPosition(grabberClosePos);
    }
}
