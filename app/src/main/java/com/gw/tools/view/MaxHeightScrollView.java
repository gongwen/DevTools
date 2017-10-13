package com.gw.tools.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.gw.tools.R;

public class MaxHeightScrollView extends ScrollView {

    private int maxHeight;
    private boolean hasMaxHeight = false;

    public MaxHeightScrollView(Context context) {
        this(context, null);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode() && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightScrollView);
            if (a.hasValue(R.styleable.MaxHeightScrollView_maxHeight)) {
                hasMaxHeight = true;
                maxHeight = a.getDimensionPixelSize(R.styleable.MaxHeightScrollView_maxHeight, 0);
            }
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (hasMaxHeight) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}