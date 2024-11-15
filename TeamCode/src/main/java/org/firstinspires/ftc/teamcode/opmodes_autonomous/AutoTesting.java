package org.firstinspires.ftc.teamcode.opmodes_autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto_execution.AutoActionHandler;
import org.firstinspires.ftc.teamcode.bot.Robot;
import org.firstinspires.ftc.teamcode.util.MathHelper;
import org.firstinspires.ftc.teamcode.util.TeleSingle;

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

        TeleSingle.init(telemetry);

        actionHandler = new AutoActionHandler(robot, telemetry);

        actionHandler.add(WAIT,false,0.33);
        actionHandler.add(MOVE,false, MathHelper.tilesToMM(1), MathHelper.tilesToMM(1), 0);

        actionHandler.init();


        robot.drive.setDriveZeroPowerBehaviorFloat();

        waitForStart();

        telemetry.addLine("Initializing");
        telemetry.update();

        while (opModeIsActive()){
            actionHandler.run();

            //robot.positionTelemetry();

            //telemetry.update();
        }
    }
}