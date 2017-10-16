package com.gw.tools.activitytracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gw.tools.lib.util.SysUtil;
import com.gw.tools.moudle.ActivityChangedEvent;
import com.gw.tools.util.CommonPool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by GongWen on 17/10/12.
 */
class ActivityTrackerView extends LinearLayout implements View.OnClickListener {
    public static final String TAG = ActivityTrackerView.class.getSimpleName();

    private final Context mContext;
    private final WindowManager mWindowManager;
    private TextView mTvPackageName;
    private TextView mTvClassName;
    private ImageView mIvClose;

    public ActivityTrackerView(Context context) {
        this(context, null);
    }

    public ActivityTrackerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initView();
    }

    private void initView() {
        inflate(mContext, R.layout.layout_floating, this);
        mTvPackageName = findViewById(R.id.tv_package_name);
        mTvClassName = findViewById(R.id.tv_class_name);
        mIvClose = findViewById(R.id.iv_close);
        mIvClose.setOnClickListener(this);
        updateText(SysUtil.getPackageName(mContext), SysUtil.getTopActivity(mContext).getShortClassName());
    }

    public void updateText(String packageName, String className) {
        mTvPackageName.setText(packageName);
        mTvClassName.setText(className);
    }

    @Subscribe
    public void onHandleEvent(ActivityChangedEvent event) {
        String packageName = event.getPackageName();
        String className = event.getClassName();
        if (className.startsWith(packageName)) {
            int PN = packageName.length();
            int CN = className.length();
            if (CN > PN && className.charAt(PN) == '.') {
                className = className.substring(PN, CN);
            }
        }
        updateText(packageName, className);
    }

    private Point preP, curP;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preP = new Point((int) event.getRawX(), (int) event.getRawY());
                break;

            case MotionEvent.ACTION_MOVE:
                curP = new Point((int) event.getRawX(), (int) event.getRawY());
                int dx = curP.x - preP.x;
                int dy = curP.y - preP.y;

                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.getLayoutParams();
                layoutParams.x += dx;
                layoutParams.y += dy;
                mWindowManager.updateViewLayout(this, layoutParams);

                preP = curP;
                break;
        }

        return false;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.iv_close == id) {
            mContext.startService(
                    new Intent(mContext, TrackerService.class)
                            .putExtra(CommonPool.EXTRA_COMMAND, CommonPool.COMMAND_CLOSE_ACTIVITY_TRACKER_WINDOW)
            );
        }
    }
}