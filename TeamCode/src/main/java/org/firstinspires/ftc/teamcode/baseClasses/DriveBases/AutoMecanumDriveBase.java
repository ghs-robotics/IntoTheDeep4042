package org.firstinspires.ftc.teamcode.baseClasses.DriveBases;

import org.firstinspires.ftc.teamcode.baseClasses.Navigation.NavigationController;

public class AutoMecanumDriveBase extends TeleMecanumDriveBase implements AutoDriveBase {

    private NavigationController xCtr;
    private NavigationController yCtr;
    private NavigationController headingCtr;

    public AutoMecanumDriveBase(MecanumDriveParameters params) { super(params); }

    @Override
    public void moveTo() {
        //TODO: Determine if moveTo initializing method is needed
    }
}
