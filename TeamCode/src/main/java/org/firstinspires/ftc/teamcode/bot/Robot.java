package org.firstinspires.ftc.teamcode.bot;

//import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.bot.drivemodes.MecanumDrive;
//import org.firstinspires.ftc.teamcode.control.cv.Camera;

public class Robot {
    HardwareMap hardwareMap;
    Telemetry telemetry;

    public MecanumDrive drive;

    public Odometry odometry;

    //FtcDashboard dashboard;

    public boolean RED;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        drive = new MecanumDrive(hardwareMap, telemetry);
        odometry = new Odometry(hardwareMap, telemetry);
    }

    /**
     * initializes the robot parts
     */
    public void init(){
        //init cameras
    }

    public void shutOff(){
        drive.calculateDrivePowers(0,0,0);
        //cam.closeCamera();
    }

    /**
     * tells the robot parts to retrieve the current information from each part to update the robot.
     */
    public void update(){
        //drive.update();
    }

    public void getAutoTelemetry(){
        //cam.getTelemetry();
        positionTelemetry();
    }

    public void getTeleOpTelemetry(){
        positionTelemetry();
    }

    public void positionTelemetry(){
        double[] pos = odometry.getPosition();
        double rot = odometry.getHeadingDeg();

        telemetry.addLine();
        telemetry.addLine("ROBOT ODOMETRY---------------------|");
        telemetry.addLine("Current pos (mm): {"+pos[0]+", "+pos[1]+"}");
        telemetry.addLine("Current rot (deg):" + rot);

        telemetry.update();

        /*telemetry.addLine("Drivebase Telemetry");
        telemetry.addData("Meta Drive Mode On: ", drive.getDriveMode());
        telemetry.addData("x pos: ", drive.getX());
        telemetry.addData("y pos: ", drive.getY());
        telemetry.addLine();
        telemetry.addData("x out", drive.getXError());
        telemetry.addData("gyro heading: ", drive.getHeading());
        telemetry.addLine(String.valueOf(drive.resetCounter));
        telemetry.addLine();*/
    }

    /*private void intakeTelemetry(){
        telemetry.addLine("Intake Telemetry");
        telemetry.addData("intake position: ", intake.getIntakePosition());
        telemetry.addLine();
    }

    private void deliveryTelemetry () {
        telemetry.addLine("Delivery System Telemetry");
        telemetry.addData("hang mode status:", delivery.getHangModeStatus());
        telemetry.addData("lift position: ", delivery.getLift1Position());
        telemetry.addData("lift position: ", delivery.getLift2Position());
        telemetry.addData("extension position: ", delivery.getExtensionPosition());
        telemetry.addData("drop position", delivery.getDropPosition());
//        telemetry.addData("touch sensor status", delivery.getTouchSensorStatus());
        telemetry.addLine();
    }*/
}
