package org.firstinspires.ftc.teamcode.bot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.bot.drivemodes.MecanumDrive;
import org.firstinspires.ftc.teamcode.bot.Odometry.Odometry;
import org.firstinspires.ftc.teamcode.util.MathHelper;

public class Robot {
    HardwareMap hardwareMap;
    Telemetry telemetry;

    public MecanumDrive drive;

    public Odometry odometry;

    public boolean RED;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        drive = new MecanumDrive(hardwareMap, telemetry);
        odometry = new Odometry(hardwareMap, telemetry);
    }

    public void shutOff(){
        drive.calculateDrivePowers(0,0,0);
    }

    public void getAutoTelemetry(){
        positionTelemetry();
    }

    public void getTeleOpTelemetry(){
        positionTelemetry();
    }

    public void positionTelemetry(){
        double[] pos = odometry.getPosition();

        telemetry.addLine();
        telemetry.addLine("ROBOT ODOMETRY---------------------|");
        telemetry.addLine("Current pos (mm): {"
            + MathHelper.round100(pos[0]) + ", "
            + MathHelper.round100(pos[1]) + "}");
        telemetry.addLine("Current pos (tiles): {"
            + MathHelper.round100(MathHelper.mmToTiles(pos[0])) + ", "
            + MathHelper.round100(MathHelper.mmToTiles(pos[1])) + "}");

        telemetry.addLine("Current rot (deg):" + MathHelper.round100(pos[2]));
    }
}
