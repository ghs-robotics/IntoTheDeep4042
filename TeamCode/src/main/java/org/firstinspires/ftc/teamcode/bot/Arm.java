package org.firstinspires.ftc.teamcode.bot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.MathHelper;

public class Arm {

    private Telemetry telemetry;

    private DcMotor rotationM1;
    private DcMotor rotationM2;

    private DcMotor extensionM1;
    private DcMotor extensionM2;

    private boolean limitsEnabled = true;

    private boolean autoMoving = false;
    private boolean startAutoOnExt = false;
    //If the extension motor position is above this value, the arm will retract first in auto
    private static final int autoMaxExtForRot = 1500;
    private int currentAutoStep = 0;

    private int rotTargetPos;
    private int extTargetPos;

    private int relativeRotPos = 0;

    //min and max motor positions to prevent hardware issues.
    private static final int minRotPos = 0;
    private static final int maxRotPos = 3250;

    private static final int minExtPos = 0;
    private static final int maxLoweredExtPos = 2500;
    private static final int maxRaisedExtPos = 4000;

    private static final int startRotPos = 1600;

    private static final int loweredRotThreshold = 2250;

    public Arm(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        rotationM1 = hardwareMap.get(DcMotor.class, "rotationLift1");
        rotationM2 = hardwareMap.get(DcMotor.class, "rotationLift2");
        extensionM1 = hardwareMap.get(DcMotor.class, "extensionLift1");
        //extensionM2 = hardwareMap.get(DcMotor.class, "extensionLift2");

        rotationM1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotationM2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extensionM1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //extensionM2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rotationM1.setDirection(DcMotorSimple.Direction.FORWARD);
        rotationM2.setDirection(DcMotorSimple.Direction.FORWARD);
        extensionM1.setDirection(DcMotorSimple.Direction.FORWARD);
        //extensionM2.setDirection(DcMotorSimple.Direction.FORWARD);

        resetEncoders();
    }

    //Clamps controller input to keep motors for continuing past hardware limit
    //Then sets motor power to input
    public void armControllerMovement(double rotInput, double extInput) {
        if (autoMoving) autoMove();
        else {
            double maxExtPos = getRotM1Position() > loweredRotThreshold ? maxLoweredExtPos : maxRaisedExtPos;

            if (limitsEnabled) {
                if (getRotM1Position() <= minRotPos) rotInput = MathHelper.clamp(rotInput,0,1);
                if (getRotM1Position() >= maxRotPos) rotInput = MathHelper.clamp(rotInput,-1,0);
                if (extensionM1.getCurrentPosition() <= minExtPos) extInput = MathHelper.clamp(extInput,0,1);
                if (extensionM1.getCurrentPosition() >= maxExtPos) extInput = MathHelper.clamp(extInput,-1,0);
            }

            rotInput = smoothRotInput(rotInput);

            rotationM1.setPower(-rotInput);
            rotationM2.setPower(-rotInput);
            extensionM1.setPower(extInput);
            //extensionM2.setPower(extInput);
        }
    }

    //Initializes auto movement
    public void setAutoMove(int posID) {
        autoMoving = true;
        //If lift is extended, first retract
        startAutoOnExt = extensionM1.getCurrentPosition() > autoMaxExtForRot;

        switch (posID) {
            case 0: //min position
                rotTargetPos = maxRotPos - 400;
                extTargetPos = minExtPos + 250;
                break;
            case 1: //top bin position
                rotTargetPos = minRotPos + 250;
                extTargetPos = maxRaisedExtPos - 115;
                break;
            case 2: //Fit in box
                rotTargetPos = startRotPos;
                extTargetPos = minExtPos;
                break;
        }
    }

    //Called every frame of auto movement
    //Performs Ext and Rot in an order based on if we need to retract lift first
    public boolean autoMove() {
        switch (currentAutoStep) {
            case 0:
                if (startAutoOnExt) autoDriveExt();
                else autoDriveRot();
                return false;

            case 1:
                if (!startAutoOnExt) autoDriveExt();
                else autoDriveRot();
                return false;

            case 2:
                stopAuto();
                break;
        }
        return true;
    }

    private void autoDriveRot() {
        double error = getRotM1Position() - rotTargetPos;
        if (Math.abs(error) > 15) {
            rotationM1.setPower(Math.signum(error) * -0.75);
            rotationM2.setPower(Math.signum(error) * -0.75);
        }
        else {
            rotationM1.setPower(0);
            rotationM2.setPower(0);
            currentAutoStep++;
        }
    }
    private void autoDriveExt() {
        double error = extensionM1.getCurrentPosition() - extTargetPos;
        if (Math.abs(error) > 15) {
            extensionM1.setPower(Math.signum(error) * -0.75);
            //extensionM2.setPower(Math.signum(error) * -0.75);
        }
        else {
            extensionM1.setPower(0);
            //extensionM2.setPower(0);
            currentAutoStep++;
        }
    }

    public void stopAuto() {
        autoMoving = false;
        currentAutoStep = 0;
    }

    public void resetEncoders() {
        rotationM1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotationM2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extensionM1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //extensionM2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rotationM1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rotationM2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extensionM1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //extensionM2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        relativeRotPos = 0;
    }
    public void setEncodersTeleStartPos() {
        resetEncoders();
        relativeRotPos = startRotPos;
    }
    public void setLimitState(boolean buttonPressed) { limitsEnabled = !buttonPressed; }

    //Smooths input between 1 and 0 as the rotation motor approaches its min and max positions
    //Cushions stopping of motor on endpoints
    private double smoothRotInput(double rotInput) {
        double p = 6; // must be even and >= 2; adjusts aggressiveness of dampening curve
        double h = (minRotPos + maxRotPos) / 2;
        double k = 1;
        double a = -1 / Math.pow(minRotPos - h, p);
        double x = getRotM1Position();

        double minOutput = 0.2;

        return rotInput * MathHelper.clamp(a * Math.pow(x - h, p) + k, minOutput, 1);
    }

    public void printMotorPositions() {
        telemetry.addLine();
        telemetry.addLine("Arm Motor Positions---------|");
        telemetry.addLine("Rot motor 1 pos:" + getRotM1Position());
        telemetry.addLine("Rot motor 2 pos:" + getRotM2Position());
        telemetry.addLine("Ext motor 1 pos:" + extensionM1.getCurrentPosition());
        //telemetry.addLine("Ext motor 2 pos:" + extensionM2.getCurrentPosition());
    }

    private int getRotM1Position() { return rotationM1.getCurrentPosition() + relativeRotPos; }
    private int getRotM2Position() { return rotationM2.getCurrentPosition() + relativeRotPos; }
}
