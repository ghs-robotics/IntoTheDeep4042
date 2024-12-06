package org.firstinspires.ftc.teamcode.opmodes_autonomous;

import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.ARM;
import static org.firstinspires.ftc.teamcode.auto_execution.AutoActions.GRABBER;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto_execution.AutoActionHandler;
import org.firstinspires.ftc.teamcode.bot.Robot;
import org.firstinspires.ftc.teamcode.util.TeleSingle;

@Autonomous
public class AutoSetStart extends LinearOpMode {
    Robot robot;
    AutoActionHandler actionHandler;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap, telemetry);

        TeleSingle.init(telemetry);

        actionHandler = new AutoActionHandler(robot, telemetry);

        actionHandler.add(GRABBER,true, 0, 0);
        actionHandler.add(ARM,false, 2);

        actionHandler.init();

        waitForStart();

        while (opModeIsActive()){
            actionHandler.run();
        }
    }
}