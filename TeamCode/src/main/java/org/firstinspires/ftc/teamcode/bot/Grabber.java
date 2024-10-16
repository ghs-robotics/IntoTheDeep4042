package org.firstinspires.ftc.teamcode.bot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Grabber {

    private Telemetry telemetry;

    private Servo grabber;
    private Servo grabberRot;

    //private static final double grabberStartPos = 0.5;
    private static final double grabberOpenPos = 0.7;
    private static final double grabberClosePos = 0.3;

    private static final double grabberRotForwardPos = 0.7;
    private static final double grabberRotDownPos = 0.3;

    public Grabber (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        grabber = hardwareMap.get(Servo.class, "grabber");
        grabberRot = hardwareMap.get(Servo.class, "grabberRot");

        //enable if needed
//        grabber.getController().pwmEnable();
//        grabberRot.getController().pwmEnable();
//        grabber.setPosition(grabberStartPos);
//        grabberRot.setPosition(grabberRotForwardPos);
    }

    public void grabberControllerMovement(boolean open, boolean close, boolean forward, boolean down) {
        if (open) grabber.setPosition(grabberOpenPos);
        else if (close) grabber.setPosition(grabberClosePos);

        if (forward) grabber.setPosition(grabberRotForwardPos);
        else if (down) grabber.setPosition(grabberRotDownPos);
    }
}
