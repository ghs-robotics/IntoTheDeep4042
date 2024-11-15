package org.firstinspires.ftc.teamcode.util;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TeleSingle {
    public static Telemetry tele;

    public static void init(Telemetry telemetry) {
        if (tele == null) tele = telemetry;
    }
}
