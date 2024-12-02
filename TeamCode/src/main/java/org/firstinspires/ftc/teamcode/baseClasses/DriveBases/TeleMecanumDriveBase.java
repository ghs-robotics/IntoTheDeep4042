package org.firstinspires.ftc.teamcode.baseClasses.DriveBases;

public class TeleMecanumDriveBase extends MecanumDriveBase implements TeleDriveBase{

    public TeleMecanumDriveBase(MecanumDriveParameters params){ super(params); }

    @Override
    public void globalDrive(double xInput, double yInput, double rotationInput, double heading) {
        double rotR = Math.toRadians(heading - 45);
        double rotXAxisR = Math.toRadians(heading + 45);

        double[] globalX = new double[] {
                xInput * Math.cos(-rotXAxisR) - yInput * Math.sin(-rotXAxisR),
                xInput * Math.sin(-rotXAxisR) + yInput * Math.cos(-rotXAxisR)
        };
        double[] globalY = new double[] {
                xInput * Math.cos(-rotR) - yInput * Math.sin(-rotR),
                xInput * Math.sin(-rotR) + yInput * Math.cos(-rotR)
        };

        localDrive(
                globalX[0] + globalY[0],
                globalX[1] + globalY[1],
                rotationInput
        );
    }

    @Override
    public void localDrive(double xInput, double yInput, double rotationInput) {
        double leftFrontPower = rotationInput - xInput + yInput;
        double rightFrontPower = rotationInput - xInput - yInput;
        double leftBackPower = rotationInput + xInput + yInput;
        double rightBackPower = rotationInput + xInput - yInput;

        setMotorPowers(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower);
    }

    @Override
    public void setMotorPowers(double leftFront, double rightFront, double leftBack, double rightBack) {
        leftFrontDrive.setPower(leftFront);
        rightFrontDrive.setPower(rightFront);
        leftBackDrive.setPower(leftBack);
        rightBackDrive.setPower(rightBack);
    }
}
