package com.gw.tools.activitytracker;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by GongWen on 17/10/12.
 */
class ActivityChangedEvent {
    private final String mPackageName;
    private final String mClassName;
    private final AccessibilityNodeInfo nodeInfo;

    public ActivityChangedEvent(String packageName, String className) {
        this(packageName, className, null);
    }

    public ActivityChangedEvent(String packageName, String className, AccessibilityNodeInfo nodeInfo) {
        mPackageName = packageName;
        mClassName = className;
        this.nodeInfo = nodeInfo;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public String getClassName() {
        return mClassName;
    }

    public AccessibilityNodeInfo getNodeInfo() {
        return nodeInfo;
    }

    @Override
    public String toString() {
        return "ActivityChangedEvent{" +
                "mPackageName='" + mPackageName + '\'' +
                ", mClassName='" + mClassName + '\'' +
                ", nodeInfo=" + nodeInfo +
                '}';
    }
}