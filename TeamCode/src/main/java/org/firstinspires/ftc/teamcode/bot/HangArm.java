package org.firstinspires.ftc.teamcode.bot;

import org.firstinspires.ftc.teamcode.baseClasses.Components.MotorParameters;
import org.firstinspires.ftc.teamcode.baseClasses.Components.TeleMotor;

import java.util.LinkedHashMap;

public class HangArm {
    private TeleMotor motor;

    public HangArm() {
        motor = new TeleMotor(new MotorParameters(
            "Hang",
            new LinkedHashMap<String, Double>() {{
                put("min", 0.0);
                put("max", 300.0);
            }}
        ));
    }

    public void changePosition() { motor.cyclePositions(false, true); }

    public void update() { motor.update(); }
}