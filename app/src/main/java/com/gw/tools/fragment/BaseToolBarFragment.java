package com.gw.tools.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gw.tools.R;
import com.gw.tools.lib.util.ResCompat;

import butterknife.BindView;

/**
 * Created by GongWen on 17/9/27.
 */

public abstract class BaseToolBarFragment extends BaseFragment {
    @BindView(R.id.toolBar)
    protected Toolbar toolbar;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitleTextColor(ResCompat.getColor(mActivity, R.color.colorToolbarText));
    }
}
