package org.firstinspires.ftc.teamcode.bot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.MathHelper;

public class Arm {

    private Telemetry telemetry;

    private DcMotor rotation1;
    private DcMotor rotation2;

    private DcMotor extension1;
    private DcMotor extension2;

    private boolean limitsEnabled = true;

    //min and max motor positions to prevent hardware issues.
    private static final double minRotPos = 0;
    private static final double minExtPos = 0;
    private static final double maxRotPos = 5000;
    private static final double maxExtPos = 4000;

    public Arm(HardwareMap hardwareMap, Telemetry telemetry, boolean auto) {
        this.telemetry = telemetry;

        rotation1 = hardwareMap.get(DcMotor.class, "rotationLift");
        extension1 = hardwareMap.get(DcMotor.class, "extensionLift");

        rotation1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extension1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rotation1.setDirection(DcMotorSimple.Direction.FORWARD);
        extension1.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoders();

        if (auto) {
            rotation1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extension1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    //Clamps controller input to keep motors for continuing past hardware limit
    //Then sets motor power to input
    public void armControllerMovement(double rotInput, double extInput) {
        if (limitsEnabled) {
            if (rotation1.getCurrentPosition() <= minRotPos) {
                rotInput = MathHelper.clamp(rotInput,0,1);
            }
            if (rotation1.getCurrentPosition() >= maxRotPos) {
                rotInput = MathHelper.clamp(rotInput,-1,0);
            }
            if (extension1.getCurrentPosition() <= minExtPos) {
                extInput = MathHelper.clamp(extInput,0,1);
            }
            if (extension1.getCurrentPosition() >= maxExtPos) {
                extInput = MathHelper.clamp(extInput,-1,0);
            }
        }

        //rotInput = smoothRotInput(rotInput);

        rotation1.setPower(rotInput);
        extension1.setPower(extInput);
    }

    //Smooths input between 1 and 0 as the rotation motor approaches its min and max positions
    private double smoothRotInput(double rotInput) {
        double h = (minRotPos + maxRotPos) / 2;
        double k = 1;
        double a = -1 / Math.pow(minRotPos - h, 2);
        double x = rotation1.getCurrentPosition();

        return rotInput * (a * Math.pow(x - h, 2) + k);
    }

    public void resetEncoders() {
        rotation1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extension1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rotation1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extension1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void setLimitState(boolean buttonPressed) { limitsEnabled = !buttonPressed; }

    public void printMotorPositions() {
        telemetry.addLine();
        telemetry.addLine("Arm Motor Positions---------|");
        telemetry.addLine("Rot motor pos:" + rotation1.getCurrentPosition());
        telemetry.addLine("Ext motor pos:" + extension1.getCurrentPosition());
        telemetry.addLine("Rot port: " + rotation1.getPortNumber());
        telemetry.addLine("Ext port: " + extension1.getPortNumber());
    }
}
