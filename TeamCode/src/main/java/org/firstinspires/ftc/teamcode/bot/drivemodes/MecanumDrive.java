package org.firstinspires.ftc.teamcode.bot.drivemodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.bot.Odometry.Odometry;

public class MecanumDrive {

    private DcMotor leftFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightFrontDrive;
    private DcMotor rightBackDrive;

    //Input
    private double inputScalerX = -0.7;
    private double inputScalerY = -0.7;
    private double inputScalerRot = 0.6;

    private Odometry odo;


    public MecanumDrive(HardwareMap hardwareMap, Odometry odo){
        this.odo = odo;
        // Gets the motor from the hub, make sure the name matches the config on the Driver hub
        leftFrontDrive = hardwareMap.get(DcMotor.class, "lf");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lb");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rf");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rb");

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setDrivePowers(double lf, double lb, double rf, double rb){
        leftFrontDrive.setPower(lf);
        leftBackDrive.setPower(lb);
        rightFrontDrive.setPower(rf);
        rightBackDrive.setPower(rb);
    }

    public void globalDrive(double xInput, double yInput, double rotInput) {
        double currentRot = odo.getPosition()[2];

        double rotR = Math.toRadians(currentRot - 45);
        double rotXAxisR = Math.toRadians(currentRot + 45);

        double[] globalX = new double[] {
            xInput * Math.cos(-rotXAxisR) - yInput * Math.sin(-rotXAxisR),
            xInput * Math.sin(-rotXAxisR) + yInput * Math.cos(-rotXAxisR)
        };
        double[] globalY = new double[] {
            xInput * Math.cos(-rotR) - yInput * Math.sin(-rotR),
            xInput * Math.sin(-rotR) + yInput * Math.cos(-rotR)
        };

        calculateDrivePowers(
            globalX[0] + globalY[0],
            globalX[1] + globalY[1],
            rotInput
        );
    }
    public void localScaledDrive(double x, double y, double rot){
        calculateDrivePowers(x * inputScalerX, y * inputScalerY, rot * inputScalerRot);
    }
    public void calculateDrivePowers(double x, double y, double rot) {
        double leftFrontPower = rot - x + y;
        double leftBackPower = rot + x + y;
        double rightFrontPower = rot - x - y;
        double rightBackPower = rot + x - y;

        setDrivePowers(leftFrontPower, leftBackPower, rightFrontPower, rightBackPower);
    }

    public void setDriveZeroPowerBehaviorFloat() {
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
}
