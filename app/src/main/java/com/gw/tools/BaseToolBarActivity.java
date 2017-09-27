package com.gw.tools;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;

/**
 * Created by GongWen on 17/9/27.
 */

public abstract class BaseToolBarActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitleTextColor(Color.WHITE);
    }

}
