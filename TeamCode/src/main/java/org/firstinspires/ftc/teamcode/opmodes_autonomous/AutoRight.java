package org.firstinspires.ftc.teamcode.opmodes_autonomous;

import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.MOVE;
import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.WAIT;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto_execution.AutoActionHandler;
import org.firstinspires.ftc.teamcode.bot.Robot;
import org.firstinspires.ftc.teamcode.util.HardwareSingle;
import org.firstinspires.ftc.teamcode.util.MathHelper;
import org.firstinspires.ftc.teamcode.util.TeleSingle;

//import org.firstinspires.ftc.teamcode.bot.Robot;

@Autonomous
public class AutoRight extends LinearOpMode {
    Robot robot;
    AutoActionHandler actionHandler;

    @Override
    public void runOpMode() throws InterruptedException {
        TeleSingle.init(telemetry);
        HardwareSingle.init(hardwareMap);

        robot = new Robot(hardwareMap, telemetry);

        actionHandler = new AutoActionHandler(robot, telemetry);

        actionHandler.add(WAIT,false,0.25);
        actionHandler.add(MOVE,false, MathHelper.tilesToMM(-1.4), -80, 0);

        actionHandler.init();

        robot.arm.setEncodersStartPos();

        waitForStart();

        while (opModeIsActive()){
            actionHandler.run();

            //robot.positionTelemetry();

            telemetry.update();
        }
    }
}