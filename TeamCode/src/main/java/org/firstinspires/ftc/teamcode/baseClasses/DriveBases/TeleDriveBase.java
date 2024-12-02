package org.firstinspires.ftc.teamcode.baseClasses.DriveBases;

public interface TeleDriveBase {
    void globalDrive(double xInput, double yInput, double headingInput, double currentHeading );

    void localDrive(double xInput, double yInput, double headingInput);

    void setMotorPowers(double leftFront, double rightFront, double leftBack, double rightBack);
}
