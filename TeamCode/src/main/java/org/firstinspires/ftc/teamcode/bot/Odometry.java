package org.firstinspires.ftc.teamcode.bot;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.util.Encoder;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Odometry {

    public static double TICKS_PER_REV = 0;
    public static double WHEEL_RADIUS = 2; // in
    public static double GEAR_RATIO = 1; // output (wheel) speed / input (encoder) speed

    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    private Encoder parallelOdometer, perpendicularOdometer;

    private BNO055IMU imu;
//    private Orientation angles;
//    private Acceleration gravity;
//    private BNO055IMU.Parameters imuParameters;

    public Odometry (HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        parallelOdometer = new Encoder(hardwareMap.get(DcMotorEx.class, "parallelEncoder"));
        perpendicularOdometer = new Encoder(hardwareMap.get(DcMotorEx.class, "perpendicularEncoder"));

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);
    }

    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    public double[] getPosition() {
        return new double[] {
                encoderTicksToInches(parallelOdometer.getCurrentPosition()),
                encoderTicksToInches(perpendicularOdometer.getCurrentPosition())
        };
    }

    public double getHeading() {
        return imu.getAngularOrientation().firstAngle;
    }
}
