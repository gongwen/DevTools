package com.gw.tools.moudle;

/**
 * Created by GongWen on 17/10/12.
 */
public class ActivityChangedEvent {
    private final String mPackageName;
    private final String mClassName;

    public ActivityChangedEvent(String packageName, String className) {
        mPackageName = packageName;
        mClassName = className;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public String getClassName() {
        return mClassName;
    }

    @Override
    public String toString() {
        return "ActivityChangedEvent{" +
                "mPackageName='" + mPackageName + '\'' +
                ", mClassName='" + mClassName + '}';
    }
}