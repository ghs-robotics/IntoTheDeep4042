package org.firstinspires.ftc.teamcode.opmodes_autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto_execution.AutoActionHandler;
import org.firstinspires.ftc.teamcode.bot.Robot;

import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.MOVE;
import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.WAIT;

//import org.firstinspires.ftc.teamcode.bot.Robot;

@Autonomous
public class AutoTesting extends LinearOpMode {
    Robot robot;
    AutoActionHandler actionHandler;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap, telemetry);
        actionHandler = new AutoActionHandler(robot, telemetry);

        actionHandler.add(WAIT,false,1);
        actionHandler.add(MOVE,false, 100, 100, 0);

        actionHandler.init();


        robot.drive.setDriveZeroPowerBehaviorFloat();

        waitForStart();

        telemetry.addLine("Initializing");
        telemetry.update();

        while (opModeIsActive()){
            actionHandler.run();

            robot.positionTelemetry();

            telemetry.update();
        }
    }
}