package org.firstinspires.ftc.teamcode.bot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.MathHelper;

public class Arm {

    private Telemetry telemetry;

    private DcMotor rotation1;
    private DcMotor rotation2;

    private DcMotor extension1;
    private DcMotor extension2;

    //min and max motor positions to prevent hardware issues.
    private static final double minRotPos = 0;
    private static final double minExtPos = 0;
    private static final double maxRotPos = 1;
    private static final double maxExtPos = 1;

    public Arm(HardwareMap hardwareMap, Telemetry telemetry, boolean auto) {
        this.telemetry = telemetry;

        rotation1 = hardwareMap.get(DcMotor.class, "rotation1");
        //rotation2 = hardwareMap.get(DcMotor.class, "rotation2");
        extension1 = hardwareMap.get(DcMotor.class, "extension1");
        //extension2 = hardwareMap.get(DcMotor.class, "extension2");

        rotation1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //rotation2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extension1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //extension2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if (auto) {
            rotation1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //rotation2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extension1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //extension2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        else {
            rotation1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //rotation2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            extension1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //extension2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    //Clamps controller input to keep motors for continuing past hardware limit
    //Then sets motor power to input
    public void armControllerMovement(double rotInput, double extInput) {
        if (rotation1.getCurrentPosition() <= minRotPos /*|| rotation2.getCurrentPosition() <= minRotPos*/) {
            rotInput = MathHelper.clamp(rotInput,0,1);
        }
        if (rotation1.getCurrentPosition() >= maxRotPos /*|| rotation2.getCurrentPosition() >= maxRotPos*/) {
            rotInput = MathHelper.clamp(rotInput,-1,0);
        }
        if (extension1.getCurrentPosition() <= minExtPos /*|| extension2.getCurrentPosition() <= minExtPos*/) {
            extInput = MathHelper.clamp(extInput,0,1);
        }
        if (extension1.getCurrentPosition() >= maxExtPos /*|| extension2.getCurrentPosition() >= maxExtPos*/) {
            extInput = MathHelper.clamp(extInput,-1,0);
        }

        rotation1.setPower(rotInput);
        //rotation2.setPower(rotInput);
        extension1.setPower(extInput);
        //extension2.setPower(extInput);
    }
}
