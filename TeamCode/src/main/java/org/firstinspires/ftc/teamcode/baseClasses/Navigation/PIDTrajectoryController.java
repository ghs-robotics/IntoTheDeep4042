package org.firstinspires.ftc.teamcode.baseClasses.Navigation;

public class PIDTrajectoryController extends PIDController{
    @Override
    public double getOuput() {
        //Determine Target and current position
        //Maybe position as parameter or have reference to odometry class
        //Consider Odometry Singleton
        double[] d = getPIDValues(0, 0);
        return 0;
    }
}
