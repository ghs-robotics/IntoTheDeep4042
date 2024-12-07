package org.firstinspires.ftc.teamcode.opmodes_autonomous;

import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.ARM;
import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.GRABBER;
import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.MOVE;
import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.WAIT;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto_execution.AutoActionHandler;
import org.firstinspires.ftc.teamcode.bot.Robot;
import org.firstinspires.ftc.teamcode.util.MathHelper;
import org.firstinspires.ftc.teamcode.util.TeleSingle;

@Autonomous
public class AutoLeft extends LinearOpMode {
    Robot robot;
    AutoActionHandler actionHandler;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap, telemetry);

        TeleSingle.init(telemetry);

        actionHandler = new AutoActionHandler(robot, telemetry);

        actionHandler.add(GRABBER,true, 0, 0);
        actionHandler.add(ARM,true, 2);
        actionHandler.add(MOVE,true, MathHelper.tilesToMM(0.95), -125, -120);
        actionHandler.add(ARM,false, 1);
        actionHandler.add(GRABBER, false,1, 0);
        actionHandler.add(WAIT, false, 0.25);
        actionHandler.add(ARM, false,2);
        actionHandler.add(MOVE, false, MathHelper.tilesToMM(-0.9), MathHelper.tilesToMM(-2.0), 85);

        actionHandler.init();

        waitForStart();

        while (opModeIsActive()){
            actionHandler.run();
        }
    }
}