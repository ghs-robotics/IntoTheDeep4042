package org.firstinspires.ftc.teamcode.auto_execution;

/*import static org.firstinspires.ftc.teamcode.control.cv.Camera.SPIKE_ZONE;
import static org.firstinspires.ftc.teamcode.control.presets.AutoPresets.centerBackDropPos;
import static org.firstinspires.ftc.teamcode.control.presets.AutoPresets.centerSpikePos;
import static org.firstinspires.ftc.teamcode.control.presets.AutoPresets.leftBackDropPos;
import static org.firstinspires.ftc.teamcode.control.presets.AutoPresets.leftSpikePos;
import static org.firstinspires.ftc.teamcode.control.presets.AutoPresets.rightBackDropPos;
import static org.firstinspires.ftc.teamcode.control.presets.AutoPresets.rightSpikePos;*/

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.bot.Robot;
import org.firstinspires.ftc.teamcode.bot.control.PIDController;
import org.firstinspires.ftc.teamcode.util.MathHelper;

public class AutoActions {

    private Robot robot;

    // identities
    public static final int DONE = -1;
    public static final int MOVE = 0;
    public static final int WAIT = 1;
    public static final int GRABBER = 2;
    public static final int ARM = 3;

    private int identity;
    private boolean endAction;

    private ElapsedTime timer;
    private boolean timerReset;

    private String description;

    private double x;
    private double y;
    private double heading; // in degrees

    private double waitTime;

    private int state;
    private int state2;

    private PIDController xPID;
    private PIDController yPID;
    private PIDController rotPID;


    public AutoActions(int id, Robot robot){
        init(id, robot);
    }

    //Used for id's: MOVE...
    public AutoActions(int id, Robot robot, int x, int y, double heading){
        this.x = MathHelper.tilesToMM(x);
        this.y = MathHelper.tilesToMM(y);
        this.heading = heading;

        //checkXSign();

        //MAYBE MAKE PID ONE DIMENSIONAL, NOT X AND Y AND ROT AT SAME TIME???
        xPID = new PIDController(this.x, false);
        yPID = new PIDController(this.y, false);
        rotPID = new PIDController(this.heading, true);

        init(id, robot);
    }

    //Used for id's: WAIT...
    public AutoActions(int id, Robot robot, double value){
        if (id == WAIT){
            waitTime = value;
        }
        init(id, robot);
    }

    //Used for id's: GRABBER ARM
    public AutoActions(int id, Robot robot, int value, int  value2){
        if (id == GRABBER || id == ARM) {
            state = value;
            state2 = value2;
        }
        init(id, robot);
    }

//    //Used for id's: MOVE?...
//    public AutoActions (int id, Robot robot, double[] pos){
//        this(id, robot, (int) pos[0], (int) pos[1], (int) pos[2]);
//    }

    private void init(int id, Robot robot) {
        this.identity = id;
        this.robot = robot;
        timerReset = false;
        timer = new ElapsedTime();

        setDescription();
    }

    /**
     * Driving the rob
     */
    private void moveTo(){
        resetTimer();

        double[] currentPos = robot.odometry.getPosition();
        double currentRot = robot.odometry.getHeadingDeg();

        double outputX = xPID.getPIDOutput(currentPos[0]);
        double outputY = yPID.getPIDOutput(currentPos[1]);
        double outputRot = rotPID.getPIDOutput(currentRot);

        boolean hasArrived = xPID.hasArrived() && yPID.hasArrived() && rotPID.hasArrived();

        if (!hasArrived) robot.drive.calculateDrivePowers(outputX, outputY, outputRot);
        else robot.drive.calculateDrivePowers(0, 0, 0);

        boolean timeOut = timer.milliseconds() > 10000;
        endAction = hasArrived || timeOut;
    }


    /**
     * waits out timer until timer is greater than or equal to the parameter wait time
     */
    private void waiting() {
        resetTimer();

        robot.drive.calculateDrivePowers(0,0,0);

        endAction = timer.milliseconds() > (waitTime * 1000);
    }

    //Sets grabber and grabberRot to specified state and ends when both reach their target position
    private void grabberState() {
        //endAction = robot.grabber.setGrabberState(state) && robot.grabber.setGrabberRotState(state2);
    }

    private void armPos() {

    }

    private void shutOffBot(){
        robot.shutOff();
    }

    /**
     * @return whether or not this action has been completed
     */
    public boolean isFinished(){
        return endAction;
    }

    /**
     * determines the action and what this specific action will do.
     */
    public void runAction(){
        switch (identity){
            case DONE:
                shutOffBot();
                break;
            case MOVE:
                moveTo();
                break;
            case WAIT:
                waiting();
                break;
            case GRABBER:
                grabberState();
                break;
            case ARM:
                armPos();
                break;
        }
    }

    /**
     * helper method to get telemetry text
     */
    private void setDescription() {
        description = "id: " + identity + "; ";
        switch (identity){
            case DONE:
                description += "Done!";
                break;
            case MOVE:
                description += "Moving to target pos: {"+x+", "+y+", "+heading+"}";
                break;
            case WAIT:
                description += "Waiting for " + waitTime + " seconds.";
                break;
        }
    }

    public String getDescription() { return description; }

    public int getIdentity() { return identity; }

    private void resetTimer(){
        if (!timerReset){
            timer.reset();
            timerReset = true;
        }
    }

//    private void checkXSign(){
//        if (!robot.RED)
//            this.x *= -1;
//
//    }
//
//    public int getTimer() {
//        return (int) (timer.milliseconds() / 1000);
//    }
}
