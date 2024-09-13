package org.firstinspires.ftc.teamcode.opmodes_teleop.input;//package org.firstinspires.ftc.teamcode.opmodes;
//
//import com.qualcomm.robotcore.hardware.Gamepad;
//
//import org.firstinspires.ftc.teamcode.opmodes.input.Controller;
//
//import java.util.ArrayList;
//
//public class TeleOpProfile extends Controller {
//    private int name;
//
//    private final String[] driverName;
//    private final String[] opName;
//
//    public boolean driverOp;
//
////    public ArrayList<Boolean> teleBooleans = new ArrayList<Boolean>();
////    public ArrayList<Double> teleDoubles = new ArrayList<Double>();
//
//    public boolean driveMode;
//    public double drivingX;
//    public double drivingY;
//    public double drivingRot;
//
//    public boolean loweringHanging;
//    public boolean raisingHanging;
//
//    public boolean raisingIntake;
//    public boolean loweringIntake;
//
//    public double pixelIn;
//    public boolean dropPixel;
//
//    public boolean retractOuttake;
//    public boolean extendOuttake;
//
//    public double powerExtension;
//
//    public double driveLift;
//
//    public double driveLeftLift;
//    public double driveRightLift;
//
//    public boolean launchDrone;
//
//    public TeleOpProfile(Gamepad gamepad, boolean driverOp) {
//        super(gamepad);
//        this.driverOp = driverOp;
//
//        name = 0;
//        driverName = new String[] {"Lillian"};
//        opName = new String[] {"Ivan"};
//
//        if (driverOp) {
//            setLillianGamepad1();
//        } else {
//            setIvanGamepad2();
//        }
//    }
//
//    public void setLillianGamepad1() {
//        driveMode = left_bumper.pressed();
//        drivingX = left_stick_x;
//        drivingY = left_stick_y;
//        drivingRot = right_stick_x;
//        loweringHanging = dpad_down.pressing();
//        raisingHanging = dpad_up.pressing();
//    }
//
//    public void setIvanGamepad2() {
//        loweringIntake = left_bumper.pressed();
//        raisingIntake = right_bumper.pressed();
//
//        pixelIn = right_trigger - left_trigger;
//
//        retractOuttake = dpad_left.pressed();
//        extendOuttake = dpad_right.pressed();
//
//        powerExtension = right_stick_y;
//
//        dropPixel = b.pressing();
//
//        driveLift = left_stick_y;
//
//        launchDrone = a.pressing();
//
//        driveLeftLift = left_stick_y;
//        driveRightLift = right_stick_y;
//    }
//
////        public void addToList1 () {
////            // gamepad 1
////            teleBooleans.add(driveMode); // 0
////            teleBooleans.add(loweringHanging); // 1
////            teleBooleans.add(raisingHanging); // 2
////
////            teleDoubles.add(drivingX); // 0
////            teleDoubles.add(drivingY); // 1
////            teleDoubles.add(drivingRot); // 2
////        }
////
////        public void addToList2 () {
////        // gamepad 2
////        teleBooleans.add(loweringIntake); // 0
////        teleBooleans.add(raisingIntake); // 1
////        teleBooleans.add(dropPixel); // 2
////        teleBooleans.add(extendOuttake); // 3
////        teleBooleans.add(launchDrone); // 4
////
////        teleDoubles.add(pixelIn); // 0
////        teleDoubles.add(driveLift); // 1
////    }
//
////    public void updateList1 () {
////        teleBooleans.set(0, driveMode);
////        teleBooleans.set(1, loweringIntake);
////        teleBooleans.set(2, raisingIntake);
////
////        teleDoubles.set(0, drivingX);
////        teleDoubles.set(1, drivingY);
////        teleDoubles.set(2, drivingRot);
////    }
////
////    public void updateList2 () {
////        teleBooleans.set(0, loweringIntake);
////        teleBooleans.set(1, raisingIntake);
////        teleBooleans.set(2, dropPixel);
////        teleBooleans.set(3, extendOuttake);
////        teleBooleans.set(4, liftSetHeight);
////        teleBooleans.set(5, liftToPosition);
////        teleBooleans.set(6, launchDrone);
////
////        teleDoubles.set(0, pixelIn);
////        teleDoubles.set(1, driveLift);
////    }
//
//    public void update() {
//        super.update();
//        if (driverOp) {
//            switch (name) {
//                case 0:
//                    setLillianGamepad1();
//                    break;
//            }
//        } else {
//            switch (name) {
//                case 0:
//                    setIvanGamepad2();
//                    break;
//            }
//        }
//    }
//}
