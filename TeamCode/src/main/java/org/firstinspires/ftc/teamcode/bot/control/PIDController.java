package org.firstinspires.ftc.teamcode.bot.control;

//import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {

    private double targetPos;

    private double error;

    private static final double maxOutput = 1;

    private double integral = 0;
    private static final double maxIntegral = 1;

    private double lastError;

    private double repetitions = 0;

    //Boolean to change constants depending on if PID is being used for position or rotation
    private boolean isPIDRot;

    private static final PIDCoefficients PIDGainPos = new PIDCoefficients(.152, .00165765, .0016622);
    private static final PIDCoefficients PIDGainRot = new PIDCoefficients(.152, .00165765, .0016622);

    private static final double arrivedDistThresholdPos = 1;
    private static final double arrivedDistThresholdRot = 1;

    private ElapsedTime PIDTimer;

    public PIDController(double targetPos, boolean isPIDRot) {
        this.targetPos = targetPos;
        this.isPIDRot = isPIDRot;
        PIDTimer = new ElapsedTime();
    }

    public double getPIDOutput (double currentPos) {
        error = currentPos - targetPos;
        if (repetitions == 0) lastError = error;

        double changeInError = lastError - error;

        integral = clamp(integral + changeInError * PIDTimer.time(), -maxIntegral, maxIntegral);
        double derivative = changeInError / PIDTimer.time();

        //Determine PID Gain for either position of rotation
        //k represents gain
        double kp = isPIDRot ? PIDGainRot.p : PIDGainPos.p;
        double ki = isPIDRot ? PIDGainRot.i : PIDGainPos.i;
        double kd = isPIDRot ? PIDGainRot.d : PIDGainPos.d;

        double P = kp * error;
        double I = ki * integral;
        double D = kd * derivative;

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

    //Returns whether or not the robot has moved close enough to its desired position or rotation.
    public boolean hasArrived() {
        return error < (isPIDRot ? arrivedDistThresholdRot : arrivedDistThresholdPos);
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
