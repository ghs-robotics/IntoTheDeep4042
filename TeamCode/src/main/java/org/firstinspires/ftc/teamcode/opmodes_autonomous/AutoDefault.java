package org.firstinspires.ftc.teamcode.opmodes_autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//import org.firstinspires.ftc.teamcode.bot.Robot;

@Autonomous
public class AutoDefault extends LinearOpMode {
    //Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        //robot = new Robot(hardwareMap, telemetry, false);
        //robot.init();

        waitForStart();

        while (opModeIsActive()){
            //robot.update();
        }
    }
}