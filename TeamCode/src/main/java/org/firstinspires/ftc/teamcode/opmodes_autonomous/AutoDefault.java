package org.firstinspires.ftc.teamcode.opmodes_autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto_execution.AutoActionHandler;
import org.firstinspires.ftc.teamcode.bot.Robot;

import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.MOVE;

//import org.firstinspires.ftc.teamcode.bot.Robot;

@Autonomous
public class AutoDefault extends LinearOpMode {
    Robot robot;
    AutoActionHandler actionHandler;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap, telemetry);
        //robot.init();
        actionHandler = new AutoActionHandler(robot, telemetry);

        actionHandler.add(MOVE, 0, 0, 0);

        actionHandler.init();
        waitForStart();

        while (opModeIsActive()){
            //robot.update();
            actionHandler.run();
            actionHandler.status();
        }
    }
}