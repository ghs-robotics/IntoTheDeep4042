package org.firstinspires.ftc.teamcode.baseClasses.Components.Old;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.baseClasses.Components.ServoParameters;
import org.firstinspires.ftc.teamcode.util.HardwareSingle;

//Servo Auto will only function if the servo is physically set to track position
public class RobotServo {
    private Servo servo;

    private ServoParameters servoParams;

    private String currentPositionId;

    public RobotServo() {
        servo = HardwareSingle.hardwareMap.get(Servo.class, servoParams.getName());

        servo.getController().pwmEnable();
    }

    public void teleTogglePosition(boolean toggle, String pos1Id, String pos2Id) {
        double pos1 = servoParams.getPositions().get(pos1Id);
        double pos2 = servoParams.getPositions().get(pos2Id);

        if (toggle) {
            if (servo.getPosition() != pos1) { setPosition(pos1, pos1Id); }
            else setPosition(pos2, pos2Id);
        }
    }

    public void teleIncrementPosition(boolean increment) {
        if (increment) {
            //servoParams.positions.
        }
    }

    public void autoSetPosition(double pos) { setPosition(pos, ""); }

    public void autoSetPositionById(String posId) { setPosition(servoParams.getPositions().get(posId), posId); }

    private void setPosition(double pos, String posId) {
        servo.setPosition(pos);
        currentPositionId = posId;
    }

}
