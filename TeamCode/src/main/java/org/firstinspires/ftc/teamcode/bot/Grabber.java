package org.firstinspires.ftc.teamcode.bot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Grabber {

    private Telemetry telemetry;

    private Servo grabber;
    private Servo grabberRot;

    //private static final double grabberStartPos = 0.5;
    private static final double grabberOpenPos = 0.2;
    private static final double grabberClosePos = 0.0;

    private static final double grabberRotForwardPos = 0.2;
    private static final double grabberRotDownPos = 0.0;

    public Grabber (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        grabber = hardwareMap.get(Servo.class, "grabber");
        grabberRot = hardwareMap.get(Servo.class, "grabberRot");

        //enable if needed
        grabber.getController().pwmEnable();
        grabberRot.getController().pwmEnable();
//        grabber.setPosition(grabberStartPos);
//        grabberRot.setPosition(grabberRotForwardPos);
    }

    public void grabberControllerMovement(boolean open, boolean close, boolean forward, boolean down) {
        if (open) grabber.setPosition(grabberOpenPos);
        else if (close) grabber.setPosition(grabberClosePos);

        if (forward) grabberRot.setPosition(grabberRotForwardPos);
        else if (down) grabberRot.setPosition(grabberRotDownPos);
    }

    //use int for boolean to simplify AutoAction constructor
    public boolean setGrabberState(int open) {
        double targetPos;
        if (open == 1) targetPos = grabberOpenPos;
        else targetPos = grabberClosePos;

        grabber.setPosition(targetPos);

        return grabber.getPosition() == targetPos;
    }

    //use int for boolean to simplify AutoAction constructor
    public boolean setGrabberRotState(int forward) {
        double targetPos;
        if (forward == 1) targetPos = grabberRotForwardPos;
        else targetPos = grabberRotDownPos;

        grabberRot.setPosition(targetPos);

        return grabberRot.getPosition() == targetPos;
    }
}
