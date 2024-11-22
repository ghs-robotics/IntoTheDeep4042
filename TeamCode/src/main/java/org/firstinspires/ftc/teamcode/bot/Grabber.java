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
    private static final double grabberClosePos = 0.750;

    private static final double grabberRotLeftPos = 0.805;
    private static final double grabberRotLeftMiddlePos = 0.64;
    private static final double grabberRotForwardPos = 0.475;
    private static final double grabberRotRightMiddlePos = 0.31;

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

    public void grabberControllerMovement(boolean toggleGrabber, boolean changeRot) {
        setGrabberState(
            toggleGrabber ? !grabberOpen : grabberOpen,
            changeRot ? (grabberRotState + 1) % 4: grabberRotState
        );
    }

    //use int for boolean to simplify AutoAction constructor
    public void setGrabberState(boolean grabberOpen, int grabberRotState) {
        this.grabberOpen = grabberOpen;
        this.grabberRotState = grabberRotState == -1 ? this.grabberRotState : grabberRotState;

        if (grabberOpen) grabber.setPosition(grabberOpenPos);
        else grabber.setPosition(grabberClosePos);

        switch (grabberRotState) {
            case 0: grabberRot.setPosition(grabberRotLeftPos); break;
            case 1: grabberRot.setPosition(grabberRotLeftMiddlePos); break;
            case 2: grabberRot.setPosition(grabberRotForwardPos); break;
            case 3: grabberRot.setPosition(grabberRotRightMiddlePos); break;
        }
    }

    public void printServoPositions() {
        telemetry.addLine();
        telemetry.addLine("Grabber Servo Positions-----------------|");
        telemetry.addLine("Grabber pos: " + grabber.getPosition());
        telemetry.addLine("GrabberRot pos: " + grabberRot.getPosition());
    }
}
