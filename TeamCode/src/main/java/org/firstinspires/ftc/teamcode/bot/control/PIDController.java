package org.firstinspires.ftc.teamcode.bot.control;

//import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.MathHelper;
import org.firstinspires.ftc.teamcode.util.TeleSingle;

public class PIDController {

    private double targetPos;

    private double error;

    private static final double maxOutput = 1;

    private double maxP = 0.9;

    private double integral = 0;
    private static final double maxIntegral = 20;

    private double lastError;

    private double repetitions = 0;

    //Boolean to change constants depending on if PID is being used for position or rotation
    private boolean isPIDRot;

    //private static final PIDCoefficients PIDGainPos = new PIDCoefficients(.0015, 0.00075, 0);
    private static final PIDCoefficients PIDGainPos = new PIDCoefficients(.001, 0.005, 0);
    private static final PIDCoefficients PIDGainRot = new PIDCoefficients(.001, 0.005, 0);

    private static final double arrivedDistThresholdPos = 3; //mm
    private static final double arrivedDistThresholdRot = 2; //deg

    private ElapsedTime PIDTimer;

    public PIDController(double targetPos, boolean isPIDRot) {
        this.targetPos = targetPos;
        this.isPIDRot = isPIDRot;
        PIDTimer = new ElapsedTime();
    }

    public double getPIDOutput (double currentPos) {
        error = currentPos - targetPos;
        if (repetitions == 0) lastError = error;

        double changeInError = error - lastError;

        integral = MathHelper.clamp(integral + error * PIDTimer.time(), -maxIntegral, maxIntegral);
        double derivative = changeInError / PIDTimer.time();

        //Determine PID Gain for either position of rotation
        //k represents gain
        double kp = isPIDRot ? PIDGainRot.p : PIDGainPos.p;
        double ki = isPIDRot ? PIDGainRot.i : PIDGainPos.i;
        double kd = isPIDRot ? PIDGainRot.d : PIDGainPos.d;

        double P = MathHelper.clamp(kp * -error, -maxP, maxP);
        double I = ki * -integral;
        double D = kd * -derivative;

        lastError = error;
        PIDTimer.reset();
        repetitions++;

        //TeleSingle.tele.addLine("P: " + MathHelper.round10k(P));
        TeleSingle.tele.addLine("I: " + MathHelper.round10k(I));
        TeleSingle.tele.addLine("Int: " + MathHelper.round10k(integral));
        //TeleSingle.tele.addLine("D: " + MathHelper.round10k(D));

        return MathHelper.clamp(P + I + D, -maxOutput, maxOutput);
    }

    //Returns whether or not the robot has moved close enough to its desired position or rotation.
    public boolean hasArrived() {
        return Math.abs(error) <= (isPIDRot ? arrivedDistThresholdRot : arrivedDistThresholdPos);
    }
}
