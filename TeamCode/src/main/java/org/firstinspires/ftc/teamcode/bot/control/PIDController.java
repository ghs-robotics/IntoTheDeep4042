package org.firstinspires.ftc.teamcode.bot.control;

//import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.MathHelper;
import org.firstinspires.ftc.teamcode.util.TeleSingle;

public class PIDController {

    private double targetPos;

    private double error;

    private static final double maxOutputPos = 1;
    private static final double maxOutputRot = 0.75;

    //private double maxP = 0.95;

    private double integral = 0;
    private static final double maxIntegralPos = 10;
    private static final double maxIntegralRot = 1.25;

    private double lastError;

    private double repetitions = 0;

    //Boolean to change constants depending on if PID is being used for position or rotation
    private boolean isPIDRot;


    private static final PIDCoefficients PIDGainPos = new PIDCoefficients(.0023, 0.006, 0.0009);
    private static final PIDCoefficients PIDGainRot = new PIDCoefficients(.012, 0.07, 0.0006);

    private static final double arrivedDistThresholdPos = 8; //mm
    private static final double arrivedDistThresholdRot = 1; //deg

    private ElapsedTime PIDTimer;

    public PIDController(double targetPos, boolean isPIDRot) {
        this.targetPos = isPIDRot ? ((targetPos + 360) % 360) : targetPos;
        this.isPIDRot = isPIDRot;
        PIDTimer = new ElapsedTime();
    }

    public double getPIDOutput (double currentPos) {
        if (isPIDRot) {
            error = ((currentPos + 360) % 360) - targetPos;
            if (error > 180) error -= 360;
        }
        else error = currentPos - targetPos;

        if (repetitions == 0) lastError = error;

        double changeInError = error - lastError;

        double maxIntegral = isPIDRot ? maxIntegralRot : maxIntegralPos;
        integral = MathHelper.clamp(integral + error * PIDTimer.time(), -maxIntegral, maxIntegral);

        double derivative = changeInError / PIDTimer.time();

        //Determine PID Gain for either position of rotation
        //k represents gain
        double kp = isPIDRot ? PIDGainRot.p : PIDGainPos.p;
        double ki = isPIDRot ? PIDGainRot.i : PIDGainPos.i;
        double kd = isPIDRot ? PIDGainRot.d : PIDGainPos.d;

        double P = kp * -error;
        double I = ki * -integral;
        double D = kd * -derivative;

        lastError = error;
        PIDTimer.reset();
        repetitions++;

        //TeleSingle.tele.addLine("P: " + MathHelper.round10k(P));
//        TeleSingle.tele.addLine("PID: "
//                + MathHelper.round10k(MathHelper.clamp(P + I + D, -maxOutput, maxOutput)));
//        TeleSingle.tele.addLine("Error: "
//                + MathHelper.round10k(error));
//        TeleSingle.tele.addLine("Integral: "
//                + MathHelper.round10k(integral));
        //TeleSingle.tele.addLine("D: " + MathHelper.round10k(D));

        double maxOutput = isPIDRot ? maxOutputRot : maxOutputPos;
        return MathHelper.clamp(P + I + D, -maxOutput, maxOutput);
    }

    //Returns whether or not the robot has moved close enough to its desired position or rotation.
    public boolean hasArrived() {
        return Math.abs(error) <= (isPIDRot ? arrivedDistThresholdRot : arrivedDistThresholdPos);
    }
}
