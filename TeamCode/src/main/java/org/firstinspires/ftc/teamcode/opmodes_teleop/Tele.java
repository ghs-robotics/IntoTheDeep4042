package org.firstinspires.ftc.teamcode.opmodes_teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.bot.Robot;
import org.firstinspires.ftc.teamcode.opmodes_teleop.input.Controller;
import org.firstinspires.ftc.teamcode.util.MathHelper;

@TeleOp
public class Tele extends LinearOpMode {
    private Robot robot;
    private Controller gp1;
    private Controller gp2;

    private Servo testServo;

    private DcMotor liftMotor1;
    private DcMotor liftMotor2;

    private boolean expInput = false;

    //The percent speed of regular movement
    private double DPadWeight = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap, telemetry);

        gp1 = new Controller(gamepad1);
        gp2 = new Controller(gamepad2);

        testServo = hardwareMap.get(Servo.class, "testServo");

        liftMotor1 = hardwareMap.get(DcMotor.class, "liftMotor1");
        liftMotor2 = hardwareMap.get(DcMotor.class, "liftMotor2");

        liftMotor1.setDirection(DcMotor.Direction.REVERSE);
        liftMotor2.setDirection(DcMotor.Direction.REVERSE);
        liftMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //robot.init();
        waitForStart();
        telemetry.addLine("Initializing");
        telemetry.update();
        //robot.update();

        while (opModeIsActive()){
            gp1.update();
            gp2.update();

            //-------------------------------------------------------------------------------------
            //                                  GAMEPAD 1
            //-------------------------------------------------------------------------------------

            if (gp1.a.pressing()) expInput = !expInput;

            double[] input;
            if (expInput) input = getExponentialInput();
            else input = getInput();

            robot.drive.calculateDrivePowers(-input[0], -input[1], input[2]);

            //-------------------------------------------------------------------------------------
            //                                  GAMEPAD 2
            //-------------------------------------------------------------------------------------

//            if (gp2.a.pressing()) {
//                testServo.setPosition(0);
//                telemetry.addLine("Pressing: A");
//            }
//            if (gp2.y.pressing()) {
//                testServo.setPosition(1);
//                telemetry.addLine("Pressing: Y");
//            }
//            else if (gp2.x.pressed()) {
//                double pos = testServo.getPosition();
//                telemetry.addLine("Servo Pos: " + pos);
//            }

            robot.arm.armControllerMovement(gp2.left_stick_y, gp2.right_stick_y);
            robot.grabber.grabberControllerMovement(
                gp2.x.pressed(), gp2.b.pressed(),gp2.y.pressed(), gp2.a.pressed()
            );

            telemetry.clear();
            telemetry.addLine();
            telemetry.addLine("Input-------------------|");
            telemetry.addLine("Lift Rot:" + gp2.left_stick_y);
            telemetry.addLine("Lift Ext:" + gp2.right_stick_y);
            telemetry.addLine("open (x): " + gp2.x.pressed());
            telemetry.addLine("closed (b): " + gp2.b.pressed());
            telemetry.addLine("forward (y): " + gp2.y.pressed());
            telemetry.addLine("down (a): " + gp2.a.pressed());

            //-------------------------------------------------------------------------------------
            //                                  TELEMETRY
            //-------------------------------------------------------------------------------------

            telemetry.update();
        }
    }

    private double[] getInput() {
        double xInput = MathHelper.clamp(
                gp1.left_stick_x
                        + (gp1.dpad_right.pressing() ? DPadWeight : 0)
                        - (gp1.dpad_left.pressing() ? DPadWeight : 0),
                -1f,
                1f
        );
        double yInput = MathHelper.clamp(
                gp1.left_stick_y
                        + (gp1.dpad_up.pressing() ? DPadWeight : 0)
                        - (gp1.dpad_down.pressing() ? DPadWeight : 0),
                -1f,
                1f
        );
        double rotInput = gp1.right_stick_x;

        return new double[] {xInput, yInput, rotInput};
    }

    public double[] getExponentialInput() {
        double sqrX = gp1.left_stick_x * gp1.left_stick_x;
        double sqrY = gp1.left_stick_y * gp1.left_stick_y;
        double sqrRot = gp1.right_stick_x * gp1.right_stick_x;

        return new double[] {sqrX, sqrY, sqrRot};
    }
}
