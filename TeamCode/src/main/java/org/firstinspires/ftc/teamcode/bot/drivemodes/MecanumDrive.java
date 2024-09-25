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
    private double inputScalerX = 0.7;
    private double inputScalerY = 0.7;
    private double inputScalerRot = 0.5;


    public MecanumDrive(HardwareMap hardwareMap, Telemetry telemetry){

        // Gets the motor from the hub, make sure the name matches the config on the Driver hub
        leftFrontDrive = hardwareMap.get(DcMotor.class, "lf");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lb");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rf");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rb");

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
        //scale input so motor doesn't necessarily run at full throttle
        x *= inputScalerX;
        y *= inputScalerY;
        rot *= inputScalerRot;

        double leftFrontPower = rot - x + y;
        double leftBackPower = rot + x + y;
        double rightFrontPower = rot - x - y;
        double rightBackPower = rot + x - y;

        setDrivePowers(leftFrontPower, leftBackPower, rightFrontPower, rightBackPower);
    }
}
