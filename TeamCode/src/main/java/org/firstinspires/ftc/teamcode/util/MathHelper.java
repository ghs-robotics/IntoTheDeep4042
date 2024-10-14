package org.firstinspires.ftc.teamcode.util;

public class MathHelper {

    private static final double TILES_TO_MM = 66;

    public static double clamp(double value, double min, double max) {
        if (value > max) return max;
        if (value < min) return min;
        return value;
    }

    //converts mm to length in floor tiles
    public static double mmToTiles(double mm) {
        return mm / TILES_TO_MM;
    }
    //converts length in floor tiles to mm
    public static double tilesToMM(double tiles) {
        return tiles * TILES_TO_MM;
    }
}
