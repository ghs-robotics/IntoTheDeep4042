package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareSingle {
    public static HardwareMap hardwareMap;

    public static void init(HardwareMap hardwareMap) {
        if (HardwareSingle.hardwareMap == null) HardwareSingle.hardwareMap = hardwareMap;
    }
}
