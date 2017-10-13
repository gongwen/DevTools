package com.gw.tools.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.gw.tools.R;
import com.gw.tools.activitytracker.CommandPool;
import com.gw.tools.activitytracker.TrackerService;
import com.gw.tools.lib.adapter.RecyclerViewAdapter;
import com.gw.tools.lib.util.SettingCompat;
import com.gw.tools.lib.util.ToastUtil;
import com.gw.tools.lib.util.ViewHolder;

import java.util.Arrays;

public class MainFragment extends BaseRecyclerViewFragment implements RecyclerViewAdapter.OnItemClickListener {

    protected int getLayoutId() {
        return R.layout.layout_base_recyclerview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setTitle("Android Dev Tools");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter.setData(Arrays.asList(getResources().getStringArray(R.array.mainItemList)));
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private RecyclerViewAdapter<String> adapter = new RecyclerViewAdapter<String>(getActivity()) {
        @Override
        protected int getLayoutId() {
            return R.layout.layout_main_item;
        }

        @Override
        protected void onBindView(ViewHolder holder, int position) {
            holder.setText(R.id.textView, mList.get(position));
        }
    };

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                mActivity.addFragment(new DeviceInfoFragment());
                break;
            case 1:
                if (!SettingCompat.canDrawOverlays(mActivity)) {
                    ToastUtil.shortToast(mActivity, "请先授予 \"DevTools\" 悬浮窗权限");
                    SettingCompat.openAppDetailsSettings(mActivity);
                } else {
                    if(SettingCompat.checkAccessibility(mActivity)){
                        mActivity.startService(
                                new Intent(mActivity, TrackerService.class)
                                        .putExtra(CommandPool.EXTRA_COMMAND, CommandPool.COMMAND_OPEN_ACTIVITY_TRACKER_WINDOW)
                        );
                    }
                }
                break;
        }
    }
}
