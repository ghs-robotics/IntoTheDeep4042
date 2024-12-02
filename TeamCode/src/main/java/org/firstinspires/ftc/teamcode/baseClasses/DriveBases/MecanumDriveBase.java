package org.firstinspires.ftc.teamcode.baseClasses.DriveBases;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.util.HardwareSingle;

public abstract class MecanumDriveBase {

    protected DcMotor leftFrontDrive;
    protected DcMotor rightFrontDrive;
    protected DcMotor leftBackDrive;
    protected DcMotor rightBackDrive;

    public MecanumDriveBase() {
        //TODO: Create MecanumDriveParameters; add name[]
        leftFrontDrive = HardwareSingle.hardwareMap.get(DcMotor.class, "leftFront");
        rightFrontDrive = HardwareSingle.hardwareMap.get(DcMotor.class, "rightFront");
        leftBackDrive = HardwareSingle.hardwareMap.get(DcMotor.class, "leftBack");
        rightBackDrive = HardwareSingle.hardwareMap.get(DcMotor.class, "rightBack");

        //TODO: set direction[] in MecanumDriveParameters
        leftFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        //TODO: set zeroPowerBehavior in MecanumDriveParameters
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        leftFrontDrive.setZeroPowerBehavior(behavior);
        rightFrontDrive.setZeroPowerBehavior(behavior);
        leftBackDrive.setZeroPowerBehavior(behavior);
        rightBackDrive.setZeroPowerBehavior(behavior);
    }
}
