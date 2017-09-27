package com.gw.tools.lib.util;

/**
 * Created by GongWen on 17/10/10.
 */

public class StringUtil {
    private StringUtil() {
    }

    public static int length(CharSequence charSequence) {
        return charSequence == null ? 0 : charSequence.length();
    }

    public static String toString(Object obj) {
        return toString(obj, "null");
    }

    public static String toString(Object obj, String resultOfEmpty) {
        if (obj == null) {
            return resultOfEmpty;
        }
        return obj instanceof String ? (String) obj : obj.toString();
    }
}
