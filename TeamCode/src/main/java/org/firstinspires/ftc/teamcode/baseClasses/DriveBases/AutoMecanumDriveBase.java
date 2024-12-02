package org.firstinspires.ftc.teamcode.baseClasses.DriveBases;

import org.firstinspires.ftc.teamcode.bot.control.PIDController;

public class AutoMecanumDriveBase extends TeleMecanumDriveBase implements AutoDriveBase {

    private PIDController xPID;
    private PIDController yPID;
    private PIDController headingPID;

    public AutoMecanumDriveBase() {
        super();
        //TODO: add params ^^^
    }
    @Override
    public void moveTo() {

    }
}
