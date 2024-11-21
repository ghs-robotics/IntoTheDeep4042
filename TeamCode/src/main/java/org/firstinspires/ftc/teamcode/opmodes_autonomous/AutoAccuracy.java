package org.firstinspires.ftc.teamcode.opmodes_autonomous;

import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.MOVE;
import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.WAIT;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto_execution.AutoActionHandler;
import org.firstinspires.ftc.teamcode.bot.Robot;
import org.firstinspires.ftc.teamcode.util.MathHelper;
import org.firstinspires.ftc.teamcode.util.TeleSingle;

//import org.firstinspires.ftc.teamcode.bot.Robot;

@Autonomous
public class AutoAccuracy extends LinearOpMode {
    Robot robot;
    AutoActionHandler actionHandler;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap, telemetry);

        TeleSingle.init(telemetry);

        actionHandler = new AutoActionHandler(robot, telemetry);

        actionHandler.add(WAIT,false,0.33);
//        actionHandler.add(MOVE,false, 0, 0, 20);


        actionHandler.add(MOVE,false, MathHelper.tilesToMM(0), MathHelper.tilesToMM(1), 0);
        actionHandler.add(WAIT,false,0.25);
        actionHandler.add(MOVE,false, MathHelper.tilesToMM(0), MathHelper.tilesToMM(0), 0);
        actionHandler.add(WAIT,false,0.25);
        actionHandler.add(MOVE,false, MathHelper.tilesToMM(0.25), MathHelper.tilesToMM(1), 90);
        actionHandler.add(WAIT,false,0.25);
        actionHandler.add(MOVE,false, MathHelper.tilesToMM(-0.25), MathHelper.tilesToMM(1), -90);
        actionHandler.add(WAIT,false,0.25);
        actionHandler.add(MOVE,false, MathHelper.tilesToMM(-0.25), MathHelper.tilesToMM(1), 90);
        actionHandler.add(WAIT,false,0.25);
        actionHandler.add(MOVE,false, MathHelper.tilesToMM(0), MathHelper.tilesToMM(0), 0);


        actionHandler.init();

        waitForStart();

        while (opModeIsActive()){
            actionHandler.run();

            //robot.positionTelemetry();

            telemetry.update();
        }
    }
}