package com.gw.tools.lib.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

/**
 * Created by GongWen on 17/10/12.
 */

public class SysUtil {
    private SysUtil() {
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static List<ActivityManager.RunningTaskInfo> getRunningTaskInfo(Context context) {
        return getRunningTaskInfo(context, Integer.MAX_VALUE);
    }

    public static List<ActivityManager.RunningTaskInfo> getRunningTaskInfo(Context context, int maxNum) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return activityManager.getRunningTasks(maxNum);
    }

    public static ComponentName getTopActivity(Context context) {
        List<ActivityManager.RunningTaskInfo> forGroundActivity = getRunningTaskInfo(context, 1);
        ActivityManager.RunningTaskInfo currentActivity = forGroundActivity.get(0);
        return currentActivity.topActivity;
    }
}
