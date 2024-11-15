package org.firstinspires.ftc.teamcode.bot.Odometry;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class Odometry {

    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    private GoBildaPinpointDriver PPD;

    public Odometry(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        PPD = hardwareMap.get(GoBildaPinpointDriver.class,"PinpointComputer");

        /*
        Set the odometry pod positions relative to the point that the odometry computer tracks around.
        The X pod offset refers to how far sideways from the tracking point the
        X (forward) odometry pod is. Left of the center is a positive number,
        right of center is a negative number. the Y pod offset refers to how far forwards from
        the tracking point the Y (strafe) odometry pod is. forward of center is a positive number,
        backwards is a negative number.
         */
        PPD.setOffsets(0, 75);

        PPD.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);

        PPD.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED,
            GoBildaPinpointDriver.EncoderDirection.REVERSED);

        PPD.resetPosAndIMU();
    }

    public double[] getPosition() {
        PPD.update();

        Pose2D pos = PPD.getPosition();
        return new double[] {
            pos.getX(DistanceUnit.MM),
            pos.getY(DistanceUnit.MM),
            -pos.getHeading(AngleUnit.DEGREES) // -180 to 180
        };
    }

    public double[] getRelativePosition(double x, double y, double heading) {
        double[] currentPos = getPosition();
        return new double[] {currentPos[0] + x, currentPos[1] + y, currentPos[2] + heading};
    }
//    public void printPos() {
//        PPD.update();
//
//        Pose2D pos = PPD.getPosition();
//        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(DistanceUnit.MM), pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES));
//        telemetry.addData("Position", data);
//    }
}
