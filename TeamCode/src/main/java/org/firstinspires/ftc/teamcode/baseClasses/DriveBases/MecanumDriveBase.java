package org.firstinspires.ftc.teamcode.baseClasses.DriveBases;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.HardwareSingle;

public abstract class MecanumDriveBase {

    protected DcMotor leftFrontDrive;
    protected DcMotor rightFrontDrive;
    protected DcMotor leftBackDrive;
    protected DcMotor rightBackDrive;

    protected MecanumDriveParameters params;

    public MecanumDriveBase(MecanumDriveParameters params) {
        this.params = params;

        String[] names = params.getMotorNames();
        leftFrontDrive = HardwareSingle.hardwareMap.get(DcMotor.class, names[0]);
        rightFrontDrive = HardwareSingle.hardwareMap.get(DcMotor.class, names[1]);
        leftBackDrive = HardwareSingle.hardwareMap.get(DcMotor.class, names[2]);
        rightBackDrive = HardwareSingle.hardwareMap.get(DcMotor.class, names[3]);

        DcMotor.Direction[] directions = params.getMotorDirections();
        leftFrontDrive.setDirection(directions[0]);
        rightFrontDrive.setDirection(directions[1]);
        leftBackDrive.setDirection(directions[2]);
        rightBackDrive.setDirection(directions[3]);

        setZeroPowerBehavior(params.getZeroPowerBehavior());

        //TODO: Determine if runMode ever needs changed using Mecanum drive
        setRunMode(params.getRunMode());
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        leftFrontDrive.setZeroPowerBehavior(behavior);
        rightFrontDrive.setZeroPowerBehavior(behavior);
        leftBackDrive.setZeroPowerBehavior(behavior);
        rightBackDrive.setZeroPowerBehavior(behavior);
    }

    private void setRunMode(DcMotor.RunMode runMode) {
        leftFrontDrive.setMode(runMode);
        rightFrontDrive.setMode(runMode);
        leftBackDrive.setMode(runMode);
        rightBackDrive.setMode(runMode);
    }
}
