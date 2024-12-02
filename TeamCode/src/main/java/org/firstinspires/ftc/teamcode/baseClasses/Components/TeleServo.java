package org.firstinspires.ftc.teamcode.baseClasses.Components;

import org.firstinspires.ftc.teamcode.util.TeleSingle;

import java.util.Iterator;

public class TeleServo extends RoboticsServo implements TeleComponent {

    private String currentPosKey = "";

    public TeleServo(ServoParameters params) {
        super(params);
    }

    @Override
    public void moveTo(double pos) {
        if (params.continuousRotation()) return;

        setPos(pos, "");
    }

    @Override
    public void moveToStored(String key) {
        if (params.continuousRotation()) return;

        setPos(params.positions().get(key), key);
    }

    @Override
    public void axisDrive(double input) {
        if (!params.continuousRotation()) return;

        crServo.setPower(input);
    }

    @Override
    public void CyclePositions(boolean left, boolean right) {
        if (params.continuousRotation()) return;

        if (right) {
            String nextKey = getNextPositionKey();

            if (nextKey == "") {
                TeleSingle.tele.addLine("nextKey could not be found in servoParams.positions");
            }
            else setPos(params.positions().get(nextKey), nextKey);
        }
        else if (left) {
            String previousKey = getPreviousPositionKey();

            if (previousKey == "") {
                TeleSingle.tele.addLine("previousKey could not be found in servoParams.positions");
            }
            else setPos(params.positions().get(previousKey), previousKey);
        }
    }

    private String getNextPositionKey() {
        Iterator itr = params.positions().keySet().iterator();

        if (currentPosKey == "") {
            if (itr.hasNext()) return (String) itr.next();
            return "";
        }

        while (itr.hasNext()) {
            String key = (String) itr.next();

            if (key == currentPosKey) {
                if (itr.hasNext()) return (String) itr.next();
                else return params.positions().keySet().iterator().next();
            }
        }
        return "";
    }

    private String getPreviousPositionKey() {
        Iterator itr = params.positions().keySet().iterator();

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
                    for (String keys : params.positions().keySet()) lastKey = keys;
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
