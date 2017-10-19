package com.gw.tools.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.gw.tools.R;
import com.gw.tools.activitytracker.TrackerService;
import com.gw.tools.fragment.dialog.EncodeTransformerDialogFragment;
import com.gw.tools.fragment.dialog.HashBase64UrlEncodeDialogFragment;
import com.gw.tools.lib.adapter.RecyclerViewAdapter;
import com.gw.tools.lib.util.SettingCompat;
import com.gw.tools.lib.util.ToastUtil;
import com.gw.tools.lib.util.ViewHolder;
import com.gw.tools.util.CommonPool;

import java.util.Arrays;

public class MainFragment extends BaseRecyclerViewFragment implements RecyclerViewAdapter.OnItemClickListener {
    private static final int REQUEST_CODE_OVERLAY_PERMISSION = 0x0;
    private static final int REQUEST_CODE_ACCESSIBILITY = 0x1;

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
                if (SettingCompat.checkAccessibility(this, REQUEST_CODE_ACCESSIBILITY)) {
                    startAccessibilityService();
                    showActivityTrackerWindow();
                }
                break;
            case 2:
                SettingCompat.openOtherApp(mActivity, "com.gudong.appkit");
                break;
            case 3:
                HashBase64UrlEncodeDialogFragment.newInstance("Hash-Base64-UrlEncode").show(getFragmentManager(), HashBase64UrlEncodeDialogFragment.class.getSimpleName());
                break;
            case 4:
                EncodeTransformerDialogFragment.newInstance("Charset相互转换").show(getFragmentManager(), HashBase64UrlEncodeDialogFragment.class.getSimpleName());
                break;
            case 5:
                ToastUtil.shortToast(mActivity, "开发中");
                //EncryptFragment.newInstance(itemNames.get(position)).show(getFragmentManager(), HashBase64UrlEncodeDialogFragment.class.getSimpleName());
                break;
            case 6:
                ToastUtil.shortToast(mActivity, "开发中");
                //EncryptFragment.newInstance(itemNames.get(position)).show(getFragmentManager(), HashBase64UrlEncodeDialogFragment.class.getSimpleName());
                break;
        }
    }

    private void startAccessibilityService() {
        mActivity.startService(
                new Intent(mActivity, TrackerService.class)
                        .putExtra(CommonPool.EXTRA_COMMAND, CommonPool.COMMAND_OPEN_ACTIVITY_TRACKER_WINDOW)
        );
    }

    private void showActivityTrackerWindow() {
        if (!SettingCompat.canDrawOverlays(mActivity)) {
            ToastUtil.shortToast(mActivity, "请先授予 \"DevTools\" 悬浮窗权限");
            SettingCompat.goManageOverlayPermission(this, REQUEST_CODE_OVERLAY_PERMISSION);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ACCESSIBILITY) {
            if (SettingCompat.isAccessibilitySettingsOn(mActivity)) {
                startAccessibilityService();
                showActivityTrackerWindow();
            }
        } else if (requestCode == REQUEST_CODE_OVERLAY_PERMISSION) {
            if (SettingCompat.canDrawOverlays(mActivity)) {
                startAccessibilityService();
            }
        }
    }
}
