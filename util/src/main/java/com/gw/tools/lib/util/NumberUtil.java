package com.gw.tools.lib.util;

/**
 * Created by GongWen on 17/9/29.
 */

public class NumberUtil {
    private NumberUtil() {
    }

    public static String formatDotForNumber(double value, int num) {
        return String.format("%." + num + "f", value);
    }
}
