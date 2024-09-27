package org.firstinspires.ftc.teamcode.bot.control;

//import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {

    private double targetPos;

//    private double maxOutput; Do I want this???

    private double integral = 0;
    private static final double maxIntegral = 1;

    private double lastError;

    private double repetitions = 0;

    private static final PIDCoefficients PIDWeight = new PIDCoefficients(.152, .00165765, .0016622);

    private ElapsedTime PIDTimer;

    public PIDController(double targetPos) {
        this.targetPos = targetPos;
        PIDTimer = new ElapsedTime();
    }

    public double getPIDOutput (double currentPos) {
        double error = currentPos - targetPos;
        if (repetitions == 0) lastError = error;

        double changeInError = lastError - error;

        integral = clamp(integral + changeInError * PIDTimer.time(), -maxIntegral, maxIntegral);
        double derivative = changeInError / PIDTimer.time();

        double P = PIDWeight.p * error;
        double I = PIDWeight.i * integral;
        double D = PIDWeight.d * derivative;

        lastError = error;
        PIDTimer.reset();
        repetitions ++;

        return P + I + D;
    }

    private double clamp(double value, double min, double max) {
        if (value > max) return max;
        if (value < min) return min;
        return value;
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
