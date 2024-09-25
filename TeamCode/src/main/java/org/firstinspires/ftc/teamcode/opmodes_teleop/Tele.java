package org.firstinspires.ftc.teamcode.opmodes_teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.bot.Robot;
import org.firstinspires.ftc.teamcode.opmodes_teleop.input.Controller;

@TeleOp
public class Tele extends LinearOpMode {
    Robot robot;
    Controller gp1;
    Controller gp2;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap, telemetry);

        gp1 = new Controller(gamepad1);
        gp2 = new Controller(gamepad2);

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

            if (gp1.a.pressed()){
                telemetry.addLine("DEBUGGING INPUT------------------------|");
                telemetry.addLine("left_stick_x: " + (gp1.left_stick_x));
                telemetry.addLine("left_stick_x: " + (gp1.left_stick_y));
                telemetry.addLine("right_stick_x: " + (gp1.right_stick_x));
                telemetry.update();
            }

            robot.drive.calculateDrivePowers(gp1.left_stick_x, gp1.left_stick_y, gp1.right_stick_x);

            //-------------------------------------------------------------------------------------
            //                                  GAMEPAD 2
            //-------------------------------------------------------------------------------------


            //-------------------------------------------------------------------------------------
            //                                  TELEMETRY
            //-------------------------------------------------------------------------------------

            //robot.update();
            //robot.getTeleOpTelemetry();
        }
    }
}
