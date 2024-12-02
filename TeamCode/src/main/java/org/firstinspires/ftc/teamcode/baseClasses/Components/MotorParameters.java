package org.firstinspires.ftc.teamcode.baseClasses.Components;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.LinkedHashMap;
import java.util.Objects;

public class MotorParameters {
    private final String name;
    private final DcMotor.Direction motorDirection;
    private final LinkedHashMap<String, Double> positions;


    public MotorParameters(
            String name,
            DcMotor.Direction motorDirection,
            LinkedHashMap <String, Double> positions
    ) {
        this.name = name;
        this.motorDirection = motorDirection;
        this.positions = positions;
    }


    public String name() { return name; }
    public DcMotor.Direction motorDirection() { return motorDirection; }
    public LinkedHashMap<String, Double> positions() { return positions; }


    @NonNull
    @Override
    public String toString() {
        return "MotorParameters(" +
                    "name=" + this.name()
                + ", motorDirection=" + this.motorDirection()
                + ", positions=" + this.positions()
                + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, motorDirection, positions);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof MotorParameters) {
            MotorParameters other = (MotorParameters) obj;

            if (
                    name.equals(other.name) &&
                    motorDirection.equals(other.motorDirection) &&
                    positions().equals(other)
            ) return true;
        }
        return false;
    }
}
