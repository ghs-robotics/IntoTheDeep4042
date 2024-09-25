package org.firstinspires.ftc.teamcode.opmodes_teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.bot.Robot;
import org.firstinspires.ftc.teamcode.opmodes_teleop.input.Controller;

@TeleOp
public class Tele extends LinearOpMode {
    Robot robot;
    Controller gp1;
    Controller gp2;

    Servo testServo;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap, telemetry);

        gp1 = new Controller(gamepad1);
        gp2 = new Controller(gamepad2);

        testServo = hardwareMap.get(Servo.class, "testServo");

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

            robot.drive.calculateDrivePowers(-gp1.left_stick_x, -gp1.left_stick_y, gp1.right_stick_x);

            //-------------------------------------------------------------------------------------
            //                                  GAMEPAD 2
            //-------------------------------------------------------------------------------------


            //-------------------------------------------------------------------------------------
            //                                  TELEMETRY
            //-------------------------------------------------------------------------------------

            telemetry.update();
        }
    }
}
