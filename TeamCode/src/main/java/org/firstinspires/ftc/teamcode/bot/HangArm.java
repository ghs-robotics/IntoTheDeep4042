package org.firstinspires.ftc.teamcode.bot;

import org.firstinspires.ftc.teamcode.baseClasses.Components.MotorParameters;
import org.firstinspires.ftc.teamcode.baseClasses.Components.TeleMotor;

import java.util.LinkedHashMap;

public class HangArm {
    private TeleMotor motor;

    public HangArm() {
        motor = new TeleMotor(new MotorParameters(
            "hang",
            new LinkedHashMap<String, Double>() {{
                put("max", 250.0);
                put("min", 0.0);
            }}
        ));
    }

    public void driveArm(double input) { motor.axisDrive(input); }

    public void changePosition() { motor.cyclePositions(false, true); }

    public void update() { motor.update(); }

    public void printPosition() {motor.printPosition();}
}