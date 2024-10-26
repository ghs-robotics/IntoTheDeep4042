package org.firstinspires.ftc.teamcode.bot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.MathHelper;

public class Arm {

    private Telemetry telemetry;

    private DcMotor rotationMotor;

    private DcMotor extensionMotor;

    private boolean limitsEnabled = true;

    private boolean autoMoving = false;
    private int currentAutoStep = 0;

    private int rotTargetPos;
    private int extTargetPos;

    //min and max motor positions to prevent hardware issues.
    private static final int minRotPos = 0;
    private static final int maxRotPos = 3200;

    private static final int minExtPos = 0;
    private static final int maxExtPos = 4000;

    public Arm(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        rotationMotor = hardwareMap.get(DcMotor.class, "rotationLift");
        extensionMotor = hardwareMap.get(DcMotor.class, "extensionLift");

        rotationMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rotationMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        extensionMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoders();
    }

    //Clamps controller input to keep motors for continuing past hardware limit
    //Then sets motor power to input
    public void armControllerMovement(double rotInput, double extInput) {
        if (autoMoving) autoMove();
        else {
            if (limitsEnabled) {
                if (rotationMotor.getCurrentPosition() <= minRotPos) {
                    rotInput = MathHelper.clamp(rotInput,0,1);
                }
                if (rotationMotor.getCurrentPosition() >= maxRotPos) {
                    rotInput = MathHelper.clamp(rotInput,-1,0);
                }
                if (extensionMotor.getCurrentPosition() <= minExtPos) {
                    extInput = MathHelper.clamp(extInput,0,1);
                }
                if (extensionMotor.getCurrentPosition() >= maxExtPos) {
                    extInput = MathHelper.clamp(extInput,-1,0);
                }
            }

            //rotInput = smoothRotInput(rotInput);

            rotationMotor.setPower(rotInput);
            extensionMotor.setPower(extInput);
        }
    }

    //Smooths input between 1 and 0 as the rotation motor approaches its min and max positions
    private double smoothRotInput(double rotInput) {
        double h = (minRotPos + maxRotPos) / 2;
        double k = 1;
        double a = -1 / Math.pow(minRotPos - h, 2);
        double x = rotationMotor.getCurrentPosition();

        return rotInput * (a * Math.pow(x - h, 2) + k);
    }

    public void resetEncoders() {
        rotationMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rotationMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void setLimitState(boolean buttonPressed) { limitsEnabled = !buttonPressed; }

    public void setAutoMove(int posID) {
        autoMoving = true;

        switch (posID) {
            case 0: //min position
                rotTargetPos = minRotPos;
                extTargetPos = minExtPos;
                break;
            case 1: //top bin position
                rotTargetPos = 250;
                extTargetPos = maxExtPos;
                break;
        }
    }

    public void autoMove() {
        double difference;
        switch (currentAutoStep) {
            case 0:
                difference = rotTargetPos - rotationMotor.getCurrentPosition();
                if(Math.abs(difference) > 15) rotationMotor.setPower(Math.signum(difference) * 0.75);
                else {
                    rotationMotor.setPower(0);
                    currentAutoStep++;
                }
                break;

            case 1:
                difference = extTargetPos - extensionMotor.getCurrentPosition();
                if(Math.abs(difference) > 15) extensionMotor.setPower(Math.signum(difference) * 0.6);
                else {
                    extensionMotor.setPower(0);
                    currentAutoStep++;
                }
                break;

            case 2:
                autoMoving = false;
                currentAutoStep = 0;
                break;
        }
    }

    public void printMotorPositions() {
        telemetry.addLine();
        telemetry.addLine("Arm Motor Positions---------|");
        telemetry.addLine("Rot motor pos:" + rotationMotor.getCurrentPosition());
        telemetry.addLine("Ext motor pos:" + extensionMotor.getCurrentPosition());
    }
}
