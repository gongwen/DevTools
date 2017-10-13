package com.gw.tools.lib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by GongWen on 17/10/12.
 */

public class SettingCompat {
    private SettingCompat() {
    }

    public static void openAppDetailsSettings(Activity activity, int requestCode) {
        String action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
        Uri uri = Uri.parse("package:" + SysUtil.getPackageName(activity));
        activity.startActivityForResult(new Intent(action, uri), requestCode);
    }

    public static void openAppDetailsSettings(android.support.v4.app.Fragment fragment, int requestCode) {
        String action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
        Uri uri = Uri.parse("package:" + SysUtil.getPackageName(fragment.getContext()));
        fragment.startActivityForResult(new Intent(action, uri), requestCode);
    }

    public static void openAppDetailsSettings(android.app.Fragment fragment, int requestCode) {
        String action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
        Uri uri = Uri.parse("package:" + SysUtil.getPackageName(fragment.getActivity()));
        fragment.startActivityForResult(new Intent(action, uri), requestCode);
    }

    public static void openAppDetailsSettings(Context context) {
        String action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
        Uri uri = Uri.parse("package:" + SysUtil.getPackageName(context));
        ContextCompat.startActivity(context, new Intent(action, uri), null);
    }

    // TODO: 17/10/12 待验证
    public static boolean canDrawOverlays(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }

    //辅助功能相关#######################################################

    public static boolean checkAccessibility(Context context) {
        // 判断辅助功能是否开启
        if (!isAccessibilitySettingsOn(context)) {
            // 引导至辅助功能设置页面
            context.startActivity(
                    new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            );
            Toast.makeText(context, "请先开启 \"Activity 栈\" 的辅助功能", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }
}
