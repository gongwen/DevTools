package com.gw.tools.lib.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by GongWen on 17/10/10.
 */

public class ScreenUtil {
    private ScreenUtil() {
    }

    public static DisplayMetrics getDisplayMetrics() {
        return ResCompat.getSystemResources().getDisplayMetrics();
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static Point getDisplaySize(Context context) {
        Display defaultDisplay;
        if (context instanceof Activity) {
            defaultDisplay = ((Activity) context).getWindowManager().getDefaultDisplay();
        } else {
            defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        }
        Point outSize = new Point();
        defaultDisplay.getSize(outSize);
        return outSize;
    }

    public static Point getDisplaySize(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        Point outSize = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            defaultDisplay.getRealSize(outSize);
        } else {
            defaultDisplay.getSize(outSize);
        }
        return outSize;
    }

    public static double getScreenInches(DisplayMetrics displayMetrics, Point point) {
        return Math.sqrt(Math.pow(point.x / displayMetrics.xdpi, 2.0d) + Math.pow(point.y / displayMetrics.ydpi, 2.0d));
    }

    /**
     * refer to trinea
     *
     * @param context
     * @return
     */
    public static String getDpiDesc(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        if (displayMetrics == null) {
            return null;
        }
        return getDpiDesc(displayMetrics.densityDpi);
    }


    public static String getDpiDesc(int dpi) {
        if (dpi <= 120) {
            return "ldpi";
        }
        if (dpi <= 160) {
            return "mdpi";
        }
        if (dpi <= 240) {
            return "hdpi";
        }
        if (dpi <= 320) {
            return "xhdpi";
        }
        if (dpi <= 480) {
            return "xxhdpi";
        }
        if (dpi <= 640) {
            return "xxxhdpi";
        }
        return "too big dpi";
    }

    /**
     * https://stackoverflow.com/questions/3166501/getting-the-screen-density-programmatically-in-android
     * http://www.lai18.com/content/9623048.html
     *
     * @return
     */
    // TODO: 17/9/29
    private static String getDpiDesc() {
        float density = Resources.getSystem().getDisplayMetrics().density;
        if (density <= 0.75f) {
            return "ldpi";
        }
        if (density >= 1.0f && density < 1.5f) {
            return "mdpi";
        }
        if (density == 1.5f) {
            return "hdpi";
        }
        if (density > 1.5f && density <= 2.0f) {
            return "xhdpi";
        }
        if (density > 2.0f && density <= 3.0f) {
            return "xxhdpi";
        }
        if (density > 3.0f && density <= 4.0f) {
            return "xxxhdpi";
        }
        return "UNKOWN";
    }
}
