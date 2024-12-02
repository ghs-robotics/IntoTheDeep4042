package org.firstinspires.ftc.teamcode.baseClasses.Components;

public class TeleMotor extends AutoMotor implements TeleComponent {

    public TeleMotor(MotorParameters params) { super(params); }

    @Override
    public void axisDrive(double input) {
        motor.setPower(input);
    }

    @Override
    public void cyclePositions(boolean left, boolean right) {
        //TODO: Consider Moving get next and previous key functions to Helper class
        if (right) {
            //Get nextKey -> moveTo(params.getPositions(nextKey));
        }
        else if (left) {
            //Get previousKey -> moveTo(params.getPositions(previousKey));
        }
    }
}
