package org.firstinspires.ftc.teamcode.bot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

//import org.firstinspires.ftc.teamcode.util.Encoder;

import java.util.Base64;

public class Odometry {

    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    private Base64.Encoder parallelEncoder;
    private Base64.Encoder perpendicularEncoder;

    private BNO055IMU imu;
    private Orientation angles;
    private Acceleration gravity;
    private BNO055IMU.Parameters imuParameters;

    public Odometry (HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;


    }
}
