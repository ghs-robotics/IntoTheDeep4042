package org.firstinspires.ftc.teamcode.bot.drivemodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MecanumDrive {

    private DcMotor leftFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightFrontDrive;
    private DcMotor rightBackDrive;

    //Input
    private double inputScalerX = 0.85;
    private double inputScalerY = 0.85;
    private double inputScalerRot = 0.6;


    public MecanumDrive(HardwareMap hardwareMap, Telemetry telemetry){

        // Gets the motor from the hub, make sure the name matches the config on the Driver hub
        leftFrontDrive = hardwareMap.get(DcMotor.class, "tarquinius");
        leftBackDrive = hardwareMap.get(DcMotor.class, "jalsene");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "mctaggart");
        rightBackDrive = hardwareMap.get(DcMotor.class, "bob");

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.update();
    }

    public void setDrivePowers(double lf, double lb, double rf, double rb){
        leftFrontDrive.setPower(lf);
        leftBackDrive.setPower(lb);
        rightFrontDrive.setPower(rf);
        rightBackDrive.setPower(rb);
    }

    public void calculateDrivePowers(double x, double y, double rot) {
        double leftFrontPower = y - x + rot;
        double leftBackPower = y + x + rot;
        double rightFrontPower = y + x - rot;
        double rightBackPower = y - x - rot;

        setDrivePowers(leftFrontPower, leftBackPower, rightFrontPower, rightBackPower);
    }

}
