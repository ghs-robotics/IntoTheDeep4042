package org.firstinspires.ftc.teamcode.baseClasses.Components;

import org.firstinspires.ftc.teamcode.util.TeleSingle;

import java.util.Iterator;

public class TeleMotor extends AutoMotor implements TeleComponent {

    private String currentPosKey = "";

    public TeleMotor(MotorParameters params) { super(params); }

    @Override
    public void axisDrive(double input) {
        motor.setPower(input);
    }

    /**
     * Iterates left or right through the map of positions specified in the Motor Parameters
     * class passed into the motor constructor. If the method runs into the end of the map,
     * it will loop around to the other end.
     *
     * @param left if true, method will iterate backwards through list (right takes priority)
     * @param right if true, method will iterate forwards through list
     */
    @Override
    public void cyclePositions(boolean left, boolean right) {
        //TODO: Consider Moving get next and previous key functions to Helper class
        if (right) {
            String nextKey = getNextPositionKey();

            if (nextKey.equals("")) {
                TeleSingle.tele.addLine("nextKey could not be found in servoParams.positions");
            }
            else setMoveTo(params.getPositions().get(nextKey), nextKey);
        }
        else if (left) {
            String previousKey = getPreviousPositionKey();

            if (previousKey.equals("")) {
                TeleSingle.tele.addLine("previousKey could not be found in servoParams.positions");
            }
            else setMoveTo(params.getPositions().get(previousKey), previousKey);
        }
    }

    private String getNextPositionKey() {
        Iterator<String> itr = params.getPositions().keySet().iterator();

        //If currentPosKey is not assigned, return first key in set
        if (currentPosKey.equals("")) {
            if (itr.hasNext()) return itr.next();
            return "";
        }

        while (itr.hasNext()) {
            String key = itr.next();

            if (key.equals(currentPosKey)) {
                if (itr.hasNext()) return itr.next();
                else return params.getPositions().keySet().iterator().next(); //Returns first key in set
            }
        }
        return "";
    }

    private String getPreviousPositionKey() {
        Iterator<String> itr = params.getPositions().keySet().iterator();

        //If currentPosKey is not assigned, return first key in set
        if (currentPosKey.equals("")) {
            if (itr.hasNext()) return itr.next();
            return "";
        }

        String lastKey = null;

        while (itr.hasNext()) {
            String key = itr.next();

            if (key.equals(currentPosKey)) {
                if (lastKey != null) return lastKey;
                else {
                    //Returns last key in set
                    for (String keys : params.getPositions().keySet()) lastKey = keys;
                    return lastKey;
                }
            }

            lastKey = key;
        }
        return "";
    }

    private void setMoveTo(double pos, String key) {
        currentPosKey = key;
        moveTo(pos);
    }
}
