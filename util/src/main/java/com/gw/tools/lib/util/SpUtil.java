package com.gw.tools.lib.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by GongWen on 17/10/10.
 */

public class SpUtil {
    private SpUtil() {
    }

    public static String SP_NAME = "sp_config";

    public static boolean putString(Context context, String key, String value) {
        SharedPreferences.Editor edit = context.getSharedPreferences(SP_NAME, 0).edit();
        edit.putString(key, value);
        return edit.commit();
    }

    public static String getString(Context context, String str) {
        return getString(context, str, null);
    }

    public static String getString(Context context, String key, String defValue) {
        return context.getSharedPreferences(SP_NAME, 0).getString(key, defValue);
    }

    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences.Editor edit = context.getSharedPreferences(SP_NAME, 0).edit();
        edit.putInt(key, value);
        return edit.commit();
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    public static int getInt(Context context, String key, int defValue) {
        return context.getSharedPreferences(SP_NAME, 0).getInt(key, defValue);
    }

    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences.Editor edit = context.getSharedPreferences(SP_NAME, 0).edit();
        edit.putLong(key, value);
        return edit.commit();
    }

    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    public static long getLong(Context context, String key, long defValue) {
        return context.getSharedPreferences(SP_NAME, 0).getLong(key, defValue);
    }

    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor edit = context.getSharedPreferences(SP_NAME, 0).edit();
        edit.putBoolean(key, value);
        return edit.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return context.getSharedPreferences(SP_NAME, 0).getBoolean(key, defValue);
    }
}
