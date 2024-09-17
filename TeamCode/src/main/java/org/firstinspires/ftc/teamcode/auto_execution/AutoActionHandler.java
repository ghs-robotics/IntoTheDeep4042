package org.firstinspires.ftc.teamcode.auto_execution;

//import static org.firstinspires.ftc.teamcode.control.auto_execution.AutoActions.DELIVER;
//import static org.firstinspires.ftc.teamcode.control.auto_execution.AutoActions.DROP;
//import static org.firstinspires.ftc.teamcode.control.auto_execution.AutoActions.EXTEND;
//import static org.firstinspires.ftc.teamcode.control.auto_execution.AutoActions.LIFT;
//import static org.firstinspires.ftc.teamcode.control.cv.Camera.SPIKE_ZONE;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.bot.Robot;

import java.util.ArrayList;

public class AutoActionHandler {
    private ArrayList<AutoActions> actionList;
    private AutoActions current;

    private Robot robot;
    private Telemetry telemetry;

    private ElapsedTime timer;

    private int totalActions;
    public int zone;


    public AutoActionHandler(Robot robot, Telemetry telemetry){
        this.actionList = new ArrayList<AutoActions>();
        this.timer = new ElapsedTime();
        this.robot = robot;
        this.telemetry = telemetry;
    }

    /**
     * runs the action and calls next action in case the current action is complete.
     */
    public void run(){
        findAndSetZone();
        current.runAction();
        checkTime();
        nextAction();
    }

    /**
     * @param actionSet a pre-existing set of autoActions to add to this list
     */
    public void add(ArrayList<AutoActions> actionSet){
        actionList.addAll(actionSet);
    }


    /**
     * @param actionHandler gets a pre-existing set of actions to add to this list from a pre-built
     *                      AutoActionHandler
     */
    public void add(AutoActionHandler actionHandler){
        actionList.addAll(actionHandler.getActions());
    }

    public void add (int action, int x, int y, double heading){
        actionList.add(new AutoActions(action, robot, x, y, heading));
    }

    public void add(int action, double value) {
        actionList.add(new AutoActions(action, robot, value));
    }

    public void add(int action, int value){
        actionList.add(new AutoActions(action, robot, value));
    }

    public void add (int action, double[] pos){
        actionList.add(new AutoActions(action, robot, pos));
    }



    /**
     * @param action the identity of the action (see the public static constant in AutoActions)
     *               This one is for actions that do not require parameters
     */
    public void add(int action){
        /*if (action == DELIVER){
            add(LIFT);
            add(EXTEND);
            add(DROP);
        } else {
            actionList.add(new AutoActions(action, robot));
        }*/
    }

    /**
     * Helper function that checks if we are close to the time ending and if we need to
     * change course and park
     */
    private void checkTime(){
        if (timer.milliseconds() > 25000 && actionList.size() > 2){

        }
    }

    /**
     * Checks the status of the current action and removes the action from queue if isFinished
     * returns true.
     */
    private void nextAction(){
        if (current.isFinished()) {
            current = null;
            actionList.remove(0);
            current = actionList.get(0);
        }
    }

    /**
     * Gets the zone (spike mark) that was detected by the camera.
     */
    public void findAndSetZone(){
        //zone = robot.cam.getSpikeZone();
    }

    /**
     * @return The action list of this object.
     *
     * Made for getting presets and adding them to the main AutoRed queue
     */
    public ArrayList<AutoActions> getActions(){

        return actionList;
    }

    /**
     * starts the queue
     */
    public void init(){
        if (actionList.isEmpty())
            return;

        actionList.add(new AutoActions(AutoActions.DONE, robot));
        current = actionList.get(0);
        totalActions = actionList.size();
    }

    /**
     * @return the total number of actions queue to execute
     */
    public int getTotalActions(){
        return actionList.size();
    }

    /**
     * Prints the current step in the AutoRed and gives an idea of how complete the auto is.
     */
    public void status(){
        int currentStep = totalActions - actionList.size() + 1;

        if (current.getIdentity() != AutoActions.DONE) {
            telemetry.addLine(currentStep + " of " + totalActions + " actions");
            telemetry.addLine();
            telemetry.addLine(current.getDescription());
            troubleshooting();
        }else
            telemetry.addLine( "Done!");

        //telemetry.addLine(String.valueOf(SPIKE_ZONE));
    }

    public void troubleshooting(){
        telemetry.addLine();
        telemetry.addLine("Troubleshooting");
        //put troubleshooting telemetry here.
        telemetry.addLine(String.valueOf(current.isFinished()));

        telemetry.addLine();
    }

}
