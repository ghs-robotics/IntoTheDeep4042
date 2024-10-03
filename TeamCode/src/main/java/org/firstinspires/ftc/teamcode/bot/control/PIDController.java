package org.firstinspires.ftc.teamcode.bot.control;

//import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.MathHelper;

public class PIDController {

    private double integral = 0;
    private double maxIntegral;

    private double lastError;

    private double repetitions = 0;

    public static PIDCoefficients PIDWeight = new PIDCoefficients(1,1,1);

    private ElapsedTime PIDTimer;

    public void init(double maxIntegral) {
        this.maxIntegral = maxIntegral;
        PIDTimer = new ElapsedTime();
    }

    public double getPIDOutput (double currentPos, double targetPos ) {
        double error = currentPos - targetPos;
        if (repetitions == 0) lastError = error;

        double changeInError = lastError - error;

        integral = MathHelper.clamp(integral + changeInError * PIDTimer.time(), -maxIntegral, maxIntegral);
        double derivative = changeInError / PIDTimer.time();

        double P = PIDWeight.p * error;
        double I = PIDWeight.i * integral;
        double D = PIDWeight.d * derivative;

        lastError = error;
        PIDTimer.reset();
        repetitions ++;

        return P + I + D;
    }

//    private void moveTestMotor(double targetPosition) {
//        double error = testMotor.getCurrentPosition();
//        double lastError = 0;
//
//        /*
//         * Comparison value dependent on motor tick count
//         * Higher end motor tick count: higher value
//         * Lower end motor tick count: lower value
//         */
//        while (Math.abs(error) <= 9 /*Modify with above comments*/ && repetitions < 40 /*Modify*/) {
//            error = testMotor.getCurrentPosition() - targetPosition;
//            double changeInError = lastError - error;
//
//            integral += changeInError * PIDTimer.time();
//            double derivative = changeInError / PIDTimer.time();
//
//            double P = PIDWeight.p * error;
//            double I = PIDWeight.i * integral;
//            double D = PIDWeight.d * derivative;
//
//            testMotor.setPower(P + I + D);
//
//            error = lastError;
//            PIDTimer.reset();
//            repetitions ++;
//        }
//    }
}
