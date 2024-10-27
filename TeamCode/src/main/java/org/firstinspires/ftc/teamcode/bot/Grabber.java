package org.firstinspires.ftc.teamcode.bot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Grabber {

    private Telemetry telemetry;

    private Servo grabber;
    private Servo grabberRot;

    private boolean grabberOpen;
    private int grabberRotState;

    private static final double grabberOpenPos = 0.83;
    private static final double grabberClosePos = 0.755;

    private static final double grabberRotSidePos = 0.805;
    private static final double grabberRotForwardPos = 0.475;
    private static final double grabberRotMiddlePos = 0.64;

    public Grabber (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        grabber = hardwareMap.get(Servo.class, "grabber");
        grabberRot = hardwareMap.get(Servo.class, "grabberRot");

        //enable if needed
        grabber.getController().pwmEnable();
        grabberRot.getController().pwmEnable();

        grabberOpen = false;
        grabberRotState = 0;
    }

    public void teleStartPos() {
        grabberOpen = false;
        grabberRotState = 0;
        grabber.setPosition(grabberClosePos);
        grabberRot.setPosition(grabberRotSidePos);
    }

    public void openGrabber() {
        grabber.setPosition(grabberOpenPos);
    }

    public void grabberControllerMovement(boolean toggleGrabber, boolean toggleRot) {
        if (toggleGrabber) grabberOpen = !grabberOpen;
        if (toggleRot) grabberRotState = (grabberRotState + 1) % 3;

        if (grabberOpen) grabber.setPosition(grabberOpenPos);
        else grabber.setPosition(grabberClosePos);

        if (grabberRotState == 0) grabberRot.setPosition(grabberRotSidePos);
        else if (grabberRotState == 1) grabberRot.setPosition(grabberRotMiddlePos);
        else grabberRot.setPosition(grabberRotForwardPos);
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
        else targetPos = grabberRotSidePos;

        grabberRot.setPosition(targetPos);

        return grabberRot.getPosition() == targetPos;
    }

    public void printServoPositions() {
        telemetry.addLine();
        telemetry.addLine("Grabber Servo Positions-----------------|");
        telemetry.addLine("Grabber pos: " + grabber.getPosition());
        telemetry.addLine("GrabberRot pos: " + grabberRot.getPosition());
    }
}
