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
import java.util.List;

public class AutoActionHandler {
    private ArrayList<AutoActions> actionList;
    private List<AutoActions> currentActions;

    private Robot robot;
    private Telemetry telemetry;

    private ElapsedTime timer;

    private int totalActions;


    public AutoActionHandler(Robot robot, Telemetry telemetry){
        this.robot = robot;
        this.telemetry = telemetry;

        actionList = new ArrayList<AutoActions>();
        currentActions = new ArrayList<AutoActions>();
        timer = new ElapsedTime();
    }

    /**
     * starts the queue
     */
    public void init(){
        if (actionList.isEmpty())
            return;

        actionList.add(new AutoActions(AutoActions.DONE, false, robot));
        totalActions = actionList.size();

        addNextActionsToCurrent();

        //printStatus();
    }

    /**
     * runs the action and calls next action in case the current action is complete.
     */
    public void run(){
        for (AutoActions action : currentActions) action.runAction();

        //printStatus();

        tryNextAction();
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

    public void add (int action, boolean async, double x, double y, double heading){
        actionList.add(new AutoActions(action, async, robot, x, y, heading));
    }

    public void add (int action, boolean async, double[] pos){
        actionList.add(new AutoActions(action, async, robot, pos));
    }

    public void add(int action, boolean async, double value) {
        actionList.add(new AutoActions(action, async, robot, value));
    }

    public void add(int action, boolean async, int value, int value2) {
        actionList.add(new AutoActions(action, async, robot, value, value2));
    }

//    public void add(int action, int value){
//        actionList.add(new AutoActions(action, robot, value));
//    }

    /**
     * @param action the identity of the action (see the public static constant in AutoActions)
     *               This one is for actions that do not require parameters
     */
    public void add(int action, boolean async){
        actionList.add(new AutoActions(action, async, robot));
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
     * @return the total number of actions queue to execute
     */
    public int getTotalActions(){
        return actionList.size();
    }



    /**
     * Checks the status of the current action and removes the action from queue if isFinished
     * returns true.
     */
    private void tryNextAction(){
        for (int i = currentActions.size() - 1; i >= 0; i--) {
            if (currentActions.get(i).isFinished()) currentActions.remove(i);
        }

        if (currentActions.isEmpty()) {
            addNextActionsToCurrent();

//            telemetry.addLine();
//            telemetry.addLine("Moving to next actions:");
//            for (AutoActions action : currentActions) telemetry.addLine(action.getDescription());
//            telemetry.update();
        }
    }

    public void addNextActionsToCurrent() {
        boolean isAsync = true;
        while (isAsync) {
            AutoActions action = actionList.get(0);
            currentActions.add(action);

            actionList.remove(0);

            isAsync = action.getAsync();
        }
    }



    /**
     * Prints the current step in the Auto and gives an idea of how complete the auto is.
     */
    public void printStatus(){
        telemetry.addLine();
        telemetry.addLine("AutoActionHandler Status:");

        int currentStep = totalActions - actionList.size() + 1;

        if (currentActions.get(0).getIdentity() != AutoActions.DONE) {
            telemetry.addLine(currentStep + " of " + totalActions + " actions");
            telemetry.addLine("Current actions description:");
            for (AutoActions action : currentActions) telemetry.addLine(action.getDescription());

//            telemetry.addLine();
//            telemetry.addLine("Actions Queued:");
//            for (AutoActions action : actionList) telemetry.addLine(action.getDescription());
        }
        else telemetry.addLine( "Done!");
    }

//    public void troubleshooting(){
//        telemetry.addLine();
//        telemetry.addLine("Troubleshooting AutoActionHandler:");
//        //put troubleshooting telemetry here.
//        telemetry.addLine(String.valueOf(current.isFinished()));
//
//        telemetry.addLine();
//    }
}
