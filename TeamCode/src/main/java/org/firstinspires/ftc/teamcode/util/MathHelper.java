package org.firstinspires.ftc.teamcode.util;

public class MathHelper {

    private static final double TILES_TO_MM = 596;

    public static double clamp(double value, double min, double max) {
        if (value > max) return max;
        if (value < min) return min;
        return value;
    }

    public static double round100(double num) {
        return Math.round(num * 100) / 100.0;
    }
    public static double round10k(double num) {
        return Math.round(num * 10000) / 10000.0;
    }

    //converts mm to length in floor tiles
    public static double mmToTiles(double mm) { return mm / TILES_TO_MM; }

    //converts length in floor tiles to mm
    public static double tilesToMM(double tiles) {
        return tiles * TILES_TO_MM;
    }

    public static double angleIn360(double angle) {
        while (angle < 0) { angle += 360; }
        return angle % 360;
    }
}
