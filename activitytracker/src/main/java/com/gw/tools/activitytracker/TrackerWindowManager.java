package com.gw.tools.activitytracker;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.gw.tools.lib.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GongWen on 17/10/12.
 */

class TrackerWindowManager {
    private final Context mContext;
    private final WindowManager mWindowManager;
    private final Map<Class, View> hasAddedView = new HashMap<>();

    public TrackerWindowManager(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public void handleCommand(String command) {
        if (CommandPool.COMMAND_OPEN_ACTIVITY_TRACKER_WINDOW.equals(command)) {
            if (hasAddedView.get(ActivityTrackerView.class) != null) {
                ToastUtil.shortToast(mContext, "Activity Tracker 已开启");
                return;
            }
            View mFloatingView = new ActivityTrackerView(mContext);
            hasAddedView.put(ActivityTrackerView.class, mFloatingView);
            WindowManager.LayoutParams LAYOUT_PARAMS = getDefaultWMLayoutParams();
            mWindowManager.addView(mFloatingView, LAYOUT_PARAMS);
        } else if (CommandPool.COMMAND_CLOSE_ACTIVITY_TRACKER_WINDOW.equals(command)) {
            if (hasAddedView.get(ActivityTrackerView.class) != null) {
                mWindowManager.removeView(hasAddedView.get(ActivityTrackerView.class));
                hasAddedView.remove(ActivityTrackerView.class);
                ToastUtil.shortToast(mContext, "Activity Tracker 已关闭");
            }
        }
    }

    public WindowManager.LayoutParams getDefaultWMLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.x = 0;
        params.y = 0;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        // TODO: 17/10/12 已废弃 
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        return params;
    }
}