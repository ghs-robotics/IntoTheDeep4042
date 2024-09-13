package org.firstinspires.ftc.teamcode.opmodes_teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//import org.firstinspires.ftc.teamcode.bot.Robot;
import org.firstinspires.ftc.teamcode.opmodes_teleop.input.Controller;

@TeleOp
public class Tele extends LinearOpMode {
    //Robot robot;
     Controller gp1;
    Controller gp2;

    boolean driveMode;

    @Override
    public void runOpMode() throws InterruptedException {
        //robot = new Robot(hardwareMap, telemetry);

        gp1 = new Controller(gamepad1);
        gp2 = new Controller(gamepad2);

        driveMode = false;

        //robot.init();
        waitForStart();
        telemetry.addLine("Initializing");
        //robot.update();

        while (opModeIsActive()){
            gp1.update();
            gp2.update();

            //-------------------------------------------------------------------------------------
            //                                  GAMEPAD 1
            //-------------------------------------------------------------------------------------


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
