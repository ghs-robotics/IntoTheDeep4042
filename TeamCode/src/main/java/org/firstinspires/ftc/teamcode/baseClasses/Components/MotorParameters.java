package org.firstinspires.ftc.teamcode.baseClasses.Components;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.LinkedHashMap;
import java.util.Objects;

public class MotorParameters {
    private final String name;
    private final DcMotor.Direction motorDirection;
    private final DcMotor.RunMode runMode;
    private final DcMotor.ZeroPowerBehavior zeroPowerBehavior;
    private final LinkedHashMap<String, Double> positions;

    public MotorParameters(String name) {
        this.name = name;
        this.motorDirection = DcMotor.Direction.FORWARD;
        this.runMode = DcMotor.RunMode.RUN_USING_ENCODER;
        this.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE;
        this.positions = new LinkedHashMap<>();
    }

    public MotorParameters(String name, DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        this.name = name;
        this.motorDirection = DcMotor.Direction.FORWARD;
        this.runMode = DcMotor.RunMode.RUN_USING_ENCODER;
        this.zeroPowerBehavior = zeroPowerBehavior;
        this.positions = new LinkedHashMap<>();
    }

    public MotorParameters(String name, DcMotor.Direction motorDirection) {
        this.name = name;
        this.motorDirection = motorDirection;
        this.runMode = DcMotor.RunMode.RUN_USING_ENCODER;
        this.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE;
        this.positions = new LinkedHashMap<>();
    }

    //TODO: Figure out how to use RunMode.RUN_TO_POSITION and see if you can remove constructor redundancy
    public MotorParameters(
            String name,
            DcMotor.Direction motorDirection,
            DcMotor.RunMode runMode,
            DcMotor.ZeroPowerBehavior zeroPowerBehavior,
            LinkedHashMap <String, Double> positions
    ) {
        this.name = name;
        this.motorDirection = motorDirection;
        this.runMode = runMode;
        this.zeroPowerBehavior = zeroPowerBehavior;
        this.positions = positions;
    }


    public String getName() { return name; }
    public DcMotor.Direction getMotorDirection() { return motorDirection; }
    public DcMotor.RunMode getRunMode() { return runMode; }
    public DcMotor.ZeroPowerBehavior getZeroPowerBehavior() { return zeroPowerBehavior; }
    public LinkedHashMap<String, Double> getPositions() { return positions; }


    @NonNull
    @Override
    public String toString() {
        return "MotorParameters(" +
                    "name=" + getName()
                + ", motorDirection=" + getMotorDirection()
                + ", runMode=" + getRunMode()
                + ", zeroPowerBehavior=" + getZeroPowerBehavior()
                + ", positions=" + getPositions()
                + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, motorDirection, runMode, zeroPowerBehavior, positions);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof MotorParameters) {
            MotorParameters other = (MotorParameters) obj;

            if (
                    name.equals(other.getName()) &&
                    motorDirection.equals(other.getMotorDirection()) &&
                    runMode.equals(other.getRunMode()) &&
                    zeroPowerBehavior.equals(other.getZeroPowerBehavior()) &&
                    positions.equals(other.getPositions())
            ) return true;
        }
        return false;
    }
}
