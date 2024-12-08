package org.firstinspires.ftc.teamcode.opmodes_autonomous;

import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.ARM;
import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.GRABBER;
import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.MOVE;
import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.WAIT;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto_execution.AutoActionHandler;
import org.firstinspires.ftc.teamcode.bot.Grabber;
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

        //Drive to box
        actionHandler.add(MOVE,true, MathHelper.tilesToMM(0.825), MathHelper.tilesToMM(-0.525), -125);
        actionHandler.add(GRABBER,true, 0, 0);
        actionHandler.add(ARM,false, 1);

        //Drop
        actionHandler.add(ARM,false, 4);
        actionHandler.add(GRABBER, false,1, 0);
        actionHandler.add(WAIT, false, 0.1);
        actionHandler.add(ARM,false, 1);

        //Move to first ground block
        actionHandler.add(ARM,true, 0);
        actionHandler.add(GRABBER,true, 1, 2);
        actionHandler.add(MOVE, false, MathHelper.tilesToMM(0.75), MathHelper.tilesToMM(-0.70), 0);

        //Grab first ground block
        actionHandler.add(ARM,false, 2);
        actionHandler.add(WAIT, false, 0.1);
        actionHandler.add(GRABBER,false, 0, 2);
        actionHandler.add(WAIT, false, 0.1);
        actionHandler.add(GRABBER,true, 0, 0);
        actionHandler.add(ARM,false, 5);
        actionHandler.add(ARM,false, 6);

        //Drive to box
        actionHandler.add(MOVE,true, MathHelper.tilesToMM(0.825), MathHelper.tilesToMM(-0.525), -125);
        actionHandler.add(ARM,false, 1);

        //Drop
        actionHandler.add(ARM,false, 4);
        actionHandler.add(GRABBER, false,1, 0);
        actionHandler.add(WAIT, false, 0.1);
        actionHandler.add(ARM,false, 1);

        //Move to second ground block
        actionHandler.add(ARM,true, 0);
        actionHandler.add(GRABBER,true, 1, 2);
        actionHandler.add(MOVE, false, MathHelper.tilesToMM(1.14), MathHelper.tilesToMM(-0.70), 0);

        //Grab second ground block
        actionHandler.add(ARM,false, 2);
        actionHandler.add(WAIT, false, 0.1);
        actionHandler.add(GRABBER,false, 0, 2);
        actionHandler.add(WAIT, false, 0.1);
        actionHandler.add(GRABBER,true, 0, 0);
        actionHandler.add(ARM,false, 5);
        actionHandler.add(ARM,false, 6);

        //Drive to box
        actionHandler.add(MOVE,true, MathHelper.tilesToMM(0.825), MathHelper.tilesToMM(-0.525), -125);
        actionHandler.add(ARM,false, 1);

        //Drop
        actionHandler.add(ARM,false, 4);
        actionHandler.add(GRABBER, false,1, 0);
        actionHandler.add(WAIT, false, 0.1);
        actionHandler.add(ARM,false, 1);

        actionHandler.add(ARM,true, 0);
        actionHandler.add(GRABBER,true, 0, 2);
        actionHandler.add(MOVE,true, MathHelper.tilesToMM(0.825), MathHelper.tilesToMM(-0.525), -125);


        actionHandler.init();

        robot.arm.setEncodersStartPos();

        waitForStart();

        while (opModeIsActive()){
            actionHandler.run();
        }
    }
}