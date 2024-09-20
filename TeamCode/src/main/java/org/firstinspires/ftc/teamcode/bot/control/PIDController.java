package org.firstinspires.ftc.teamcode.bot.control;

//import com.acmerobotics.dashboard.FtcDashboard;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    DcMotor testMotor;

    double integral = 0;
    double repetitions = 0;

    public static PIDCoefficients PID = new PIDCoefficients(0,0,0);
    public static PIDCoefficients PIDWeight = new PIDCoefficients(1, 1, 1);

    //FtcDashboard dashboard;

    public static double TARGET_POS = 100; // 100 is default value

    ElapsedTime PIDTimer = new ElapsedTime();

    public void init(HardwareMap hardwareMap) {
        testMotor = hardwareMap.dcMotor.get("testMotor");

        testMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        testMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //dashboard = FtcDashboard.getInstance();
    }
    void moveTestMotor(double targetPosition) {
        double error = testMotor.getCurrentPosition();
        double lastError = 0;

        /*
         * Comparison value dependent on motor tick count
         * Higher end motor tick count: higher value
         * Lower end motor tick count: lower value
         */
        while (Math.abs(error) <= 9 /*Modify with above comments*/ && repetitions < 40 /*Modify*/) {
            error = testMotor.getCurrentPosition() - targetPosition;
            double changeInError = lastError - error;

            integral += changeInError * PIDTimer.time();
            double derivative = changeInError / PIDTimer.time();

            double P = PID.p * error;
            double I = PID.i * integral;
            double D = PID.d * derivative;

            testMotor.setPower(P + I + D);

            error = lastError;
            PIDTimer.reset();
            repetitions ++;
        }
    }
}
