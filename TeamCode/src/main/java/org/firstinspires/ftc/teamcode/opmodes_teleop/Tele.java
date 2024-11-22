package org.firstinspires.ftc.teamcode.opmodes_teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bot.Robot;
import org.firstinspires.ftc.teamcode.opmodes_teleop.input.Controller;
import org.firstinspires.ftc.teamcode.util.MathHelper;
import org.firstinspires.ftc.teamcode.util.TeleSingle;

@TeleOp
public class Tele extends LinearOpMode {
    private Robot robot;
    private Controller gp1;
    private Controller gp2;

    //The percent speed of regular movement
    private double dpadMaxWeight = 0.4;
    private double[] dpadCurrentWeight = new double[] {0, 0};
    private double dpadWeightChangeRate = 0.1;

    private double extLiftSlowScaler = 0.5;


    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap, telemetry);

        gp1 = new Controller(gamepad1);
        gp2 = new Controller(gamepad2);

        TeleSingle.init(telemetry);

        waitForStart();
        telemetry.addLine("Initializing");
        telemetry.update();

        while (opModeIsActive()){
            gp1.update();
            gp2.update();

            //-------------------------------------------------------------------------------------
            //                                  GAMEPAD 1
            //                                   Input:
            //            Forward and Strafe: left_stick | Rotation: right_stick_x
            //-------------------------------------------------------------------------------------

            double[] input = getInput();

            robot.drive.calculateDrivePowers(input[0], input[1], input[2]);

            //TODO: MOVE TO AutoSetStart
            if (gp1.left_bumper.pressing() && gp1.right_bumper.pressing()) {
                robot.grabber.setGrabberState(false, 0);
                robot.arm.setAutoMove(2);
            }

//            robot.drive.calculateDrivePowers(gp1.left_stick_x, gp1.left_stick_y, gp1.right_stick_x);

            //-------------------------------------------------------------------------------------
            //                                  GAMEPAD 2
            //                                   Input:
            //            Arm Rotation: left_stick_y | Arm Extension: right_stick_y
            //               Grabber Open: x | Close: b | Forward: y | Down: a
            //        Remove Limits: dpad_left (held) | Reset lift 0 positions: dpad_right
            //-------------------------------------------------------------------------------------

            //TODO: Move to TeleStartPos
            if (gp2.dpad_up.pressed()) robot.arm.setEncodersTeleStartPos();

            if (gp2.a.pressed()) robot.arm.stopAuto();
            else if (gp2.y.pressed()) {
                robot.grabber.setGrabberState(false, 2);
                robot.arm.setAutoMove(1);
            }
            if (gp2.x.pressed()) {
                robot.grabber.setGrabberState(true, -1);
                robot.arm.setAutoMove(0);
            }

            robot.arm.armControllerMovement(
                gp2.left_stick_y,
                gp2.right_bumper.pressing() ? gp2.right_stick_y * extLiftSlowScaler : gp2.right_stick_y
            );

            robot.arm.setLimitState(gp2.dpad_left.pressing());
            if (gp2.dpad_right.pressed()) robot.arm.resetEncoders();

            robot.grabber.grabberControllerMovement(gp2.left_bumper.pressed(),gp2.right_bumper.pressed());

            //-------------------------------------------------------------------------------------
            //                                  TELEMETRY
            //-------------------------------------------------------------------------------------

            telemetry.clear();
//            telemetry.addLine();
//            telemetry.addLine("Input-------------------|");
//            telemetry.addLine("Lift Rot:" + gp2.left_stick_y);
//            telemetry.addLine("Lift Ext:" + gp2.right_stick_y);
//            telemetry.addLine("open (x): " + gp2.x.pressed());
//            telemetry.addLine("closed (b): " + gp2.b.pressed());
//            telemetry.addLine("forward (y): " + gp2.y.pressed());
//            telemetry.addLine("down (a): " + gp2.a.pressed());

            robot.arm.printMotorPositions();
            robot.grabber.printServoPositions();

            telemetry.update();
        }
    }

    private double[] getInput() {
        if (gp1.dpad_right.pressing()) dpadCurrentWeight[0] = MathHelper.clamp(
                dpadCurrentWeight[0] + dpadWeightChangeRate, -dpadMaxWeight, dpadMaxWeight);
        else if (gp1.dpad_left.pressing()) dpadCurrentWeight[0] = MathHelper.clamp(
                dpadCurrentWeight[0] - dpadWeightChangeRate, -dpadMaxWeight, dpadMaxWeight);
        else {
            double startSign = Math.signum(dpadCurrentWeight[0]);
            dpadCurrentWeight[0] -= dpadWeightChangeRate * startSign;
            if (Math.signum(dpadCurrentWeight[0]) != startSign) dpadCurrentWeight[0] = 0;
        }


        if (gp1.dpad_up.pressing()) dpadCurrentWeight[1] = MathHelper.clamp(
                dpadCurrentWeight[1] + dpadWeightChangeRate, -dpadMaxWeight, dpadMaxWeight);
        else if (gp1.dpad_down.pressing()) dpadCurrentWeight[1] = MathHelper.clamp(
                dpadCurrentWeight[1] - dpadWeightChangeRate, -dpadMaxWeight, dpadMaxWeight);
        else {
            double startSign = Math.signum(dpadCurrentWeight[1]);
            dpadCurrentWeight[1] -= dpadWeightChangeRate * startSign;
            if (Math.signum(dpadCurrentWeight[1]) != startSign) dpadCurrentWeight[1] = 0;
        }

        double xInput = MathHelper.clamp(gp1.left_stick_x + dpadCurrentWeight[0], -1f, 1f);
        double yInput = MathHelper.clamp(gp1.left_stick_y - dpadCurrentWeight[1], -1f, 1f);

//        double xInput = MathHelper.clamp(
//                gp1.left_stick_x
//                        + (gp1.dpad_right.pressing() ? dpadCurrentWeight[0] : 0)
//                        - (gp1.dpad_left.pressing() ? dpadCurrentWeight[0] : 0),
//                -1f,
//                1f
//        );
//        double yInput = MathHelper.clamp(
//                gp1.left_stick_y
//                        - (gp1.dpad_up.pressing() ? dpadCurrentWeight[1] : 0)
//                        + (gp1.dpad_down.pressing() ? dpadCurrentWeight[1] : 0),
//                -1f,
//                1f
//        );
        double rotInput = gp1.right_stick_x;

        return new double[] {xInput, yInput, rotInput};
    }

    public double[] getExponentialInput() {
        double sqrX = gp1.left_stick_x * gp1.left_stick_x * Math.signum(gp1.left_stick_x);
        double sqrY = gp1.left_stick_y * gp1.left_stick_y * Math.signum(gp1.left_stick_y);
        double sqrRot = gp1.right_stick_x * gp1.right_stick_x * Math.signum(gp1.right_stick_x);

        return new double[] {sqrX, sqrY, sqrRot};
    }
}
