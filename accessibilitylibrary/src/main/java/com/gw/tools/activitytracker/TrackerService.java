package com.gw.tools.activitytracker;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.gw.tools.moudle.ActivityChangedEvent;
import com.gw.tools.util.CommonPool;
import com.gw.tools.util.NotificationUtil;
import com.gw.tools.util.ViewHierarchicalTreeNodeManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by GongWen on 17/10/12.
 */

public class TrackerService extends AccessibilityService {
    private static final int REQUEST_CODE_NOTIFICATION = 0X0;
    public static final String TAG = "TrackerService";
    TrackerWindowManager mTrackerWindowManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        if (mTrackerWindowManager == null) {
            mTrackerWindowManager = new TrackerWindowManager(this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        mTrackerWindowManager.handleCommand(intent.getStringExtra(CommonPool.EXTRA_COMMAND));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            String packageName = event.getPackageName().toString();
            String className = event.getClassName().toString();
            //悬浮窗样式显示
            EventBus.getDefault().post(new ActivityChangedEvent(packageName, className));
            //通知样式显示
            if (!packageName.equals(CommonPool.SYSTEM_UI_PACKAGE_NAME)
                    && !className.equals(CommonPool.ANDROID_WIDGET_FRAMELAYOUT)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ViewHierarchicalTreeNodeManager.getInstance().setTreeNode(getRootInActiveWindow());
                }
                NotificationUtil.showActivityTrackerNotification(this,
                        packageName, className, REQUEST_CODE_NOTIFICATION);
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}