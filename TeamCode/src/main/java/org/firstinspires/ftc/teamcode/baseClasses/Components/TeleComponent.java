package org.firstinspires.ftc.teamcode.baseClasses.Components;

public interface TeleComponent extends AutoComponent {
    void axisDrive(double input);

    void cyclePositions(boolean left, boolean right);
}
