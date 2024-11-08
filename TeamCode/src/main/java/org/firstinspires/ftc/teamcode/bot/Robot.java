package org.firstinspires.ftc.teamcode.bot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.bot.drivemodes.MecanumDrive;
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
        telemetry.addLine("Current pos (mm): {"+pos[0]+", "+pos[1]+"}");

        telemetry.addLine("Current pos (tiles): {" + MathHelper.mmToTiles(pos[0]) + ", "
            + MathHelper.mmToTiles(pos[1]) + "}");

        telemetry.addLine("Current rot (deg):" + pos[2]);

        telemetry.update();
    }
}
