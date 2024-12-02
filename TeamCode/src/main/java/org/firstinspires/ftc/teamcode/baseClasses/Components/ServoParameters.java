package org.firstinspires.ftc.teamcode.baseClasses.Components;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.Servo;

import java.util.LinkedHashMap;
import java.util.Objects;

public class ServoParameters {
    private final String name;
    private final boolean continuousRotation;
    private final Servo.Direction servoDirection;
    private final LinkedHashMap<String, Double> positions;


    public ServoParameters(
            String name,
            boolean continuousRotation,
            Servo.Direction servoDirection,
            LinkedHashMap <String, Double> positions
    ) {
        this.name = name;
        this.continuousRotation = continuousRotation;
        this.servoDirection = servoDirection;
        this.positions = positions;
    }


    public String name() { return name; }
    public boolean continuousRotation() { return continuousRotation; }
    public Servo.Direction servoDirection() { return servoDirection; }
    public LinkedHashMap<String, Double> positions() { return positions; }


    @NonNull
    @Override
    public String toString() {
        return "ServoParameters(" +
                    "name=" + this.name()
                + ", continuousRotation=" + this.continuousRotation()
                + ", servoDirection=" + this.servoDirection()
                + ", positions=" + this.positions()
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

            if (
                    name.equals(other.name()) &&
                    continuousRotation == other.continuousRotation() &&
                    servoDirection.equals(other.servoDirection()) &&
                    positions().equals(other.positions())
            ) return true;
        }
        return false;
    }
}
