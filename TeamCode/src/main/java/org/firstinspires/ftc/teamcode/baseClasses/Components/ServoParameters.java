package org.firstinspires.ftc.teamcode.baseClasses.Components;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.LinkedHashMap;
import java.util.Objects;

public class ServoParameters {
    private final String name;
    private final boolean continuousRotation;
    private final Servo.Direction servoDirection;
    private final LinkedHashMap<String, Double> positions;

    public ServoParameters(String name) {
        this.name = name;
        continuousRotation = false;
        servoDirection = Servo.Direction.FORWARD;
        positions = new LinkedHashMap<>();
    }

    public ServoParameters( String name, LinkedHashMap <String, Double> positions) {
        this.name = name;
        this.continuousRotation = false;
        this.servoDirection = Servo.Direction.FORWARD;
        this.positions = positions;
    }

    public ServoParameters(String name, boolean continuousRotation) {
        this.name = name;
        this.continuousRotation = continuousRotation;
        this.servoDirection = Servo.Direction.FORWARD;
        positions = new LinkedHashMap<>();
    }

    public ServoParameters(String name, boolean continuousRotation, Servo.Direction servoDirection) {
        this.name = name;
        this.continuousRotation = continuousRotation;
        this.servoDirection = servoDirection;
        positions = new LinkedHashMap<>();
    }

    public ServoParameters(
            String name,
            Servo.Direction servoDirection,
            LinkedHashMap <String, Double> positions
    ) {
        this.name = name;
        this.continuousRotation = false;
        this.servoDirection = servoDirection;
        this.positions = positions;
    }


    public String getName() { return name; }
    public boolean isContinuousRotation() { return continuousRotation; }
    public Servo.Direction getServoDirection() { return servoDirection; }
    public LinkedHashMap<String, Double> getPositions() { return positions; }


    @NonNull
    @Override
    public String toString() {
        return "ServoParameters(" +
                    "name=" + getName()
                + ", continuousRotation=" + isContinuousRotation()
                + ", servoDirection=" + getServoDirection()
                + ", positions=" + getPositions()
                + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, continuousRotation, servoDirection, positions);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof ServoParameters) {
            ServoParameters other = (ServoParameters) obj;

            return name.equals(other.getName()) &&
                   continuousRotation == other.isContinuousRotation() &&
                   servoDirection.equals(other.getServoDirection()) &&
                   positions.equals(other.getPositions());
        }
        return false;
    }
}
