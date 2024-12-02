package org.firstinspires.ftc.teamcode.baseClasses.DriveBases;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Arrays;
import java.util.Objects;

public class MecanumDriveParameters {
    private final String[] motorNames;
    private final DcMotor.Direction[] motorDirections;
    private final DcMotor.RunMode runMode;
    private final DcMotor.ZeroPowerBehavior zeroPowerBehavior;

    public MecanumDriveParameters(String[] motorNames) {
        this.motorNames = motorNames;
        this.motorDirections = new DcMotor.Direction[] {FORWARD, FORWARD, FORWARD, FORWARD};
        this.runMode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
        this.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE;
    }

    public MecanumDriveParameters(String[] motorNames, DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        this.motorNames = motorNames;
        this.motorDirections = new DcMotor.Direction[] {FORWARD, FORWARD, FORWARD, FORWARD};
        this.runMode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
        this.zeroPowerBehavior = zeroPowerBehavior;
    }

    public MecanumDriveParameters(String[] motorNames, DcMotor.Direction[] motorDirections) {
        this.motorNames = motorNames;
        this.motorDirections = motorDirections;
        this.runMode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
        this.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE;
    }

    public MecanumDriveParameters(
            String[] motorNames,
            DcMotor.Direction[] motorDirections,
            DcMotor.ZeroPowerBehavior zeroPowerBehavior
    ) {
        this.motorNames = motorNames;
        this.motorDirections = motorDirections;
        this.runMode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
        this.zeroPowerBehavior = zeroPowerBehavior;
    }

    public MecanumDriveParameters(
            String[] motorNames,
            DcMotor.Direction[] motorDirections,
            DcMotor.RunMode runMode,
            DcMotor.ZeroPowerBehavior zeroPowerBehavior
    ) {
        this.motorNames = motorNames;
        this.motorDirections = motorDirections;
        this.runMode = runMode;
        this.zeroPowerBehavior = zeroPowerBehavior;
    }


    public String[] getMotorNames() { return motorNames; }
    public DcMotor.Direction[] getMotorDirections() { return motorDirections; }
    public DcMotor.RunMode getRunMode() { return runMode; }
    public DcMotor.ZeroPowerBehavior getZeroPowerBehavior() { return zeroPowerBehavior; }


    @NonNull
    @Override
    public String toString() {
        return "MecanumDriveParameters(" +
                    "motorNames=" + Arrays.toString(getMotorNames())
                + ", motorDirections=" + Arrays.toString(getMotorDirections())
                + ", runMode=" + getRunMode()
                + ", zeroPowerBehavior=" + getZeroPowerBehavior()
                + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                Arrays.hashCode(motorNames),
                Arrays.hashCode(motorDirections),
                runMode,
                zeroPowerBehavior
        );
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof MecanumDriveParameters) {
            MecanumDriveParameters other = (MecanumDriveParameters) obj;

            return Arrays.equals(getMotorNames(), other.getMotorNames()) &&
                   Arrays.equals(getMotorDirections(), other.getMotorDirections()) &&
                   runMode.equals(other.getRunMode()) &&
                   zeroPowerBehavior.equals(other.getZeroPowerBehavior());
        }
        return false;
    }
}
