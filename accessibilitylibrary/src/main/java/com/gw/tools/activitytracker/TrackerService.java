package com.gw.tools.activitytracker;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by GongWen on 17/10/12.
 */

public class TrackerService extends AccessibilityService {
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
        mTrackerWindowManager.handleCommand(intent.getStringExtra(CommandPool.EXTRA_COMMAND));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent: " + event.toString());
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            EventBus.getDefault().post(new ActivityChangedEvent(
                    event.getPackageName().toString(),
                    event.getClassName().toString()
            ));
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