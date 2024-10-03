package org.firstinspires.ftc.teamcode.util;

public class MathHelper {

    public static double clamp(double value, double min, double max) {
        if (value > max) return max;
        if (value < min) return min;
        return value;
    }
}
