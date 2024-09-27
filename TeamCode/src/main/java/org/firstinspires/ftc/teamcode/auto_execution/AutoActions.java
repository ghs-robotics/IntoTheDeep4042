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
//import org.firstinspires.ftc.teamcode.control.NavigationPID;

public class AutoActions {

    private Robot robot;

    // identities
    public static final int DONE = -1;
    public static final int MOVE = 0;
    public static final int WAIT = 1;

    private int identity;
    private boolean endAction;

    private ElapsedTime timer;
    private boolean timerReset;

    private String description;

    double x;
    double y;
    double heading; // in degrees

    double waitTime;

    private PIDController xPID;
    private PIDController yPID;


    public AutoActions(int id, Robot robot){
        init(id, robot);
    }

    //id's: MOVE...
    public AutoActions(int id, Robot robot, int x, int y, double heading){
        this.x = x;
        this.y = y;
        this.heading = heading;

        //checkXSign();

        //MAYBE MAKE PID ONE DIMENSIONAL, NOT X AND Y AND ROT AT SAME TIME???
        xPID = new PIDController(x);
        yPID = new PIDController(y);
        //Add rotationalPID

        init(id, robot);
    }

    //id's: WAIT...
    public AutoActions(int id, Robot robot, double value){
        if (id == WAIT){
            waitTime = value;
        }
        init(id, robot);
    }

    //id's: MOVE?...
    public AutoActions (int id, Robot robot, double[] pos){
        this(id, robot, (int) pos[0], (int) pos[1], (int) pos[2]);
    }

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
        /*resetTimer();

        boolean there = robot.drive.runToPosition(xPID, yPID);
        boolean timeOut = timer.milliseconds() > 4250;
        endAction = there || timeOut;*/
    }


    /**
     * waits out timer until timer is greater than or equal to the parameter wait time
     */
    private void waiting() {
        resetTimer();

        robot.drive.calculateDrivePowers(0,0,0);

        endAction = timer.milliseconds() > (waitTime * 1000);
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
        }
    }

    /**
     * helper method to get telemetry text
     */
    private void setDescription() {
        description = "id: " + identity + "; ";
        switch (identity){
            case DONE:
                description += "No Description Provided.";
                break;
            case MOVE:
                description += "Moving";
                break;
            case WAIT:
                description += "Waiting";
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
