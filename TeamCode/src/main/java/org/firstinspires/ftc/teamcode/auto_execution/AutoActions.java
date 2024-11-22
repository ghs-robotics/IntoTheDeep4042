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
import org.firstinspires.ftc.teamcode.util.TeleSingle;

public class AutoActions {

    private Robot robot;

    // identities
    public static final int DONE = -1;
    public static final int MOVE = 0;
    public static final int WAIT = 1;
    public static final int GRABBER = 2;
    public static final int ARM = 3;

    private int identity;
    private boolean async;
    private boolean currentAction = false;
    private boolean endAction;

    private ElapsedTime timer;

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

    private double[] pidOutput = new double[] {0,0,0};


    public AutoActions(int id, boolean async, Robot robot){
        this.async = async;
        init(id, robot);
    }

    //Used for id's: MOVE...
    public AutoActions(int id, boolean async, Robot robot, double x, double y, double heading){
        this.async = async;
        this.x = x;
        this.y = y;
        this.heading = heading;

        //checkXSign();

        //MAYBE MAKE PID ONE DIMENSIONAL, NOT X AND Y AND ROT AT SAME TIME???
        xPID = new PIDController(this.x, false);
        yPID = new PIDController(this.y, false);
        rotPID = new PIDController(this.heading, true);

        init(id, robot);
    }

    //Used for id's: WAIT...
    public AutoActions(int id, boolean async, Robot robot, double value){
        this.async = async;
        if (id == WAIT){
            waitTime = value;
        }
        init(id, robot);
    }

    //Used for id's: GRABBER ARM
    public AutoActions(int id, boolean async, Robot robot, int value, int  value2){
        this.async = async;
        if (id == GRABBER || id == ARM) {
            state = value;
            state2 = value2;
        }
        init(id, robot);
    }

    private void init(int id, Robot robot) {
        this.identity = id;
        this.robot = robot;
        timer = new ElapsedTime();
    }

    /**
     * Driving the rob
     */
    private void moveTo(){
        initAction();

        double[] currentPos = robot.odo.getPosition();

        double PIDRampLimit = timer.seconds() * 3;

        TeleSingle.tele.clear();
        //TeleSingle.tele.addLine("X---------------|");
        double outputX = MathHelper.clamp(xPID.getPIDOutput(currentPos[0]), -PIDRampLimit, PIDRampLimit);
        //TeleSingle.tele.addLine("Y---------------|");
        double outputY = MathHelper.clamp(yPID.getPIDOutput(currentPos[1]), -PIDRampLimit, PIDRampLimit);
        //TeleSingle.tele.addLine("ROT-------------|");
        double outputRot = MathHelper.clamp(rotPID.getPIDOutput(currentPos[2]), -PIDRampLimit, PIDRampLimit);
        TeleSingle.tele.update();

        pidOutput = new double[] {outputX, outputY, outputRot};

        boolean hasArrived = xPID.hasArrived() && yPID.hasArrived() && rotPID.hasArrived();

        if (!hasArrived) robot.drive.globalDrive(outputX, -outputY, outputRot);
        else robot.drive.calculateDrivePowers(0, 0, 0);

        boolean timeOut = timer.milliseconds() > 10000;
        endAction = hasArrived || timeOut;
    }


    /**
     * waits out timer until timer is greater than or equal to the parameter wait time
     */
    private void waiting() {
        initAction();

        robot.drive.calculateDrivePowers(0,0,0);

        endAction = timer.milliseconds() > (waitTime * 1000);
    }

    //Sets grabber and grabberRot to specified state and ends when both reach their target position
    private void grabberState() {
        initAction();

        robot.grabber.setGrabberState(false, 0); //positions here

        endAction = true;
    }

    private void armPos() {
        if (!currentAction) robot.arm.setAutoMove(0);//Id here

        initAction();

        endAction = robot.arm.autoMove();
    }

    private void setEndAutoState(){
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
                setEndAutoState();
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
    public String getDescription() {
        String description = "id: " + identity + "; ";
        switch (identity){
            case DONE:
                description += "Done!";
                break;
            case MOVE:
                description += "Moving to target pos: {"+x+", "+y+", "+heading+"}";
                if (currentAction) description += "; PID output: {"
                        + MathHelper.round100(pidOutput[0]) + ", "
                        + MathHelper.round100(pidOutput[1]) + ", "
                        + MathHelper.round100(pidOutput[2]) + "}";
                break;
            case WAIT:
                description += "Waiting for "
                    + (currentAction ? MathHelper.round100(timer.milliseconds() / 1000) : 0)
                    +  " / " + waitTime + " seconds.";
                break;
        }

        return description;
    }

    public int getIdentity() { return identity; }
    public boolean getAsync() { return async; }

    private void initAction(){
        if (!currentAction){
            timer.reset();
            currentAction = true;
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
