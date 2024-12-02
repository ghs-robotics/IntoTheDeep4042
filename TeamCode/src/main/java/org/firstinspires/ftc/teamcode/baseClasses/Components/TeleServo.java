package org.firstinspires.ftc.teamcode.baseClasses.Components;

import org.firstinspires.ftc.teamcode.util.TeleSingle;

import java.util.Iterator;

public class TeleServo extends RoboticsServo implements TeleComponent {

    private String currentPosKey = "";

    public TeleServo(ServoParameters params) { super(params); }

    @Override
    public void moveTo(double pos) {
        if (params.isContinuousRotation()) return;

        setPos(pos, "");
    }

    @Override
    public void moveToStored(String key) {
        if (params.isContinuousRotation() || !params.getPositions().containsValue(key)) return;

        setPos(params.getPositions().get(key), key);
    }

    @Override
    public void axisDrive(double input) {
        if (!params.isContinuousRotation()) return;

        crServo.setPower(input);
    }

    @Override
    public void CyclePositions(boolean left, boolean right) {
        if (params.isContinuousRotation()) return;

        if (right) {
            String nextKey = getNextPositionKey();

            if (nextKey == "") {
                TeleSingle.tele.addLine("nextKey could not be found in servoParams.positions");
            }
            else setPos(params.getPositions().get(nextKey), nextKey);
        }
        else if (left) {
            String previousKey = getPreviousPositionKey();

            if (previousKey == "") {
                TeleSingle.tele.addLine("previousKey could not be found in servoParams.positions");
            }
            else setPos(params.getPositions().get(previousKey), previousKey);
        }
    }

    private String getNextPositionKey() {
        Iterator itr = params.getPositions().keySet().iterator();

        //If currentPosKey is not assigned, return first key in set
        if (currentPosKey == "") {
            if (itr.hasNext()) return (String) itr.next();
            return "";
        }

        while (itr.hasNext()) {
            String key = (String) itr.next();

            if (key == currentPosKey) {
                if (itr.hasNext()) return (String) itr.next();
                else return params.getPositions().keySet().iterator().next(); //Returns first key in set
            }
        }
        return "";
    }

    private String getPreviousPositionKey() {
        Iterator itr = params.getPositions().keySet().iterator();

        //If currentPosKey is not assigned, return first key in set
        if (currentPosKey == "") {
            if (itr.hasNext()) return (String) itr.next();
            return "";
        }

        String lastKey = null;

        while (itr.hasNext()) {
            String key = (String) itr.next();

            if (key == currentPosKey) {
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

    private void setPos(double pos, String key) {
        servo.setPosition(pos);
        currentPosKey = key;
    }
}
