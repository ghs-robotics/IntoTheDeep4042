package org.firstinspires.ftc.teamcode.opmodes_teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bot.Robot;
import org.firstinspires.ftc.teamcode.opmodes_teleop.input.Controller;
import org.firstinspires.ftc.teamcode.util.HardwareSingle;
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
        TeleSingle.init(telemetry);
        HardwareSingle.init(hardwareMap);

        robot = new Robot(hardwareMap, telemetry);

        gp1 = new Controller(gamepad1);
        gp2 = new Controller(gamepad2);

        robot.arm.setEncodersTeleStartPos();

        waitForStart();

        while (opModeIsActive()){
            gp1.update();
            gp2.update();

            //-------------------------------------------------------------------------------------
            //                                  GAMEPAD 1
            //                                   Input:
            //           Forward: left_stick_y | Strafe: left_stick_x | Drive Slow: Dpad
            //                           Rotation: right_stick_x
            //                          Change Hang Lift State: a
            //-------------------------------------------------------------------------------------

            double[] input = getInput();

            robot.drive.localScaledDrive(input[0], input[1], input[2]);

            //-------------------------------------------------------------------------------------
            //                                  GAMEPAD 2
            //                                   Input:
            //           Arm Rotation: left_stick_y | Arm Extension: right_stick_y
            //                   Slow Lift Extension: right_trigger (held)
            //               Auto Lift: Down: x | Box: y | Emergency Stop: a
            //      Toggle Grabber: left_bumper | Change Grabber Rotation: right_bumper
            //      Remove Limits: dpad_left (held) | Reset lift 0 positions: dpad_right
            //-------------------------------------------------------------------------------------

            if (gp2.a.pressed()) robot.arm.stopAuto();
            else if (gp2.x.pressed()) {
                robot.grabber.setGrabberState(true, -1);
                robot.arm.setAutoMove(0);
            }
            if (gp2.y.pressed()) {
                robot.grabber.setGrabberState(false, 2);
                robot.arm.setAutoMove(1);
            }

            robot.arm.armControllerMovement(
                -gp2.left_stick_y,
                gp2.right_trigger.pressing() ? -gp2.right_stick_y * extLiftSlowScaler : -gp2.right_stick_y
            );

            robot.grabber.grabberControllerMovement(gp2.left_bumper.pressed(),gp2.right_bumper.pressed());

            robot.arm.setLimitState(gp2.dpad_left.pressing());
            if (gp2.dpad_right.pressed()) robot.arm.setEncodersTeleStartPos();

            //-------------------------------------------------------------------------------------
            //                                  TELEMETRY
            //-------------------------------------------------------------------------------------

            telemetry.clear();
            telemetry.addLine();
            telemetry.addLine("Input-------------------|");
            telemetry.addLine("Right Trigger Value:" + gp2.right_trigger.getValue());
            telemetry.addLine("Right Trigger Pressing:" + gp2.right_trigger.pressing());

            robot.arm.printMotorPositions();

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
