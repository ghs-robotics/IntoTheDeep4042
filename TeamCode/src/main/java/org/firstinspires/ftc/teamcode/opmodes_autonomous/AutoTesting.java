package org.firstinspires.ftc.teamcode.opmodes_autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto_execution.AutoActionHandler;
import org.firstinspires.ftc.teamcode.bot.Robot;

import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.MOVE;

//import org.firstinspires.ftc.teamcode.bot.Robot;

@Autonomous
public class AutoTesting extends LinearOpMode {
    Robot robot;
    AutoActionHandler actionHandler;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap, telemetry);
        actionHandler = new AutoActionHandler(robot, telemetry);


        //actionHandler.add(MOVE,0,0,0);

        actionHandler.init();


        robot.drive.setDriveZeroPowerBehaviorFloat();

        waitForStart();

        telemetry.addLine("Initializing");
        telemetry.update();

        while (opModeIsActive()){
            //actionHandler.run();
            robot.positionTelemetry();
        }
    }
}