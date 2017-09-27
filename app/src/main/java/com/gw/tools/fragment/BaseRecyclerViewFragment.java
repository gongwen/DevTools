package com.gw.tools.fragment;

import android.support.v7.widget.RecyclerView;

import com.gw.tools.R;

import butterknife.BindView;

/**
 * Created by GongWen on 17/9/27.
 */

public abstract class BaseRecyclerViewFragment extends BaseToolBarFragment {
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
}
