package com.gw.tools.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.gw.tools.R;
import com.gw.tools.lib.adapter.RecyclerViewAdapter;
import com.gw.tools.lib.util.ViewHolder;
import com.gw.tools.util.DeviceUtil;
import com.gw.tools.util.DialogUtil;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by GongWen on 17/9/27.
 */

public class DeviceInfoFragment extends BaseRecyclerViewFragment implements RecyclerViewAdapter.OnItemClickListener {
    private static final int REQUEST_CODE_READ_PHONE_STATE = 1001;
    private List<String> itemNames;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    private Snackbar snackbar;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_base_recyclerview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemNames = Arrays.asList(getResources().getStringArray(R.array.deviceItemList));
        toolbar.setTitle("设备信息");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter.setData(itemNames);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        snackbar = Snackbar.make(constraintLayout, "请允许相关权限，以获取相关信息", Snackbar.LENGTH_SHORT)
                .setAction("点击申请权限", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
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
        String text;
        switch (position) {
            case 0:
                text = DeviceUtil.getScreenInfo();
                break;
            case 1:
                text = DeviceUtil.getSystemInfo();
                break;
            case 2:
                text = DeviceUtil.getHardwareInfo();
                break;
            case 3:
                text = DeviceUtil.getCPUInfo();
                break;
            case 4:
                text = DeviceUtil.getStorageInfo();
                break;
            case 5:
                // TODO: 17/9/29 权限相关 
                DialogUtil.showPermissionDialog(mActivity, "\"读取手机状态\"", v -> MPermissions.requestPermissions(DeviceInfoFragment.this, REQUEST_CODE_READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE));
                /*if (!MPermissions.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.READ_PHONE_STATE, REQUEST_CODE_READ_PHONE_STATE)) {
                    MPermissions.requestPermissions(DeviceInfoFragment.this, REQUEST_CODE_READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);
                }*/
                return;
            case 6:
                text = DeviceUtil.getNetInfo();
                break;
            case 7:
                text = DeviceUtil.getVMInfo();
                break;
            case 8:
                text = DeviceUtil.getBuildInfo();
                break;
            case 9:
                text = DeviceUtil.getBuildVersionInfo();
                break;
            case 10:
                text = DeviceUtil.getSystemPropertiesInfo();
                break;
            case 11:
                text = DeviceUtil.getSystemEnvInfo();
                break;
            default:
                return;
        }
        DialogUtil.showInfoDialog(mActivity, itemNames.get(position), text);
    }

    /*@ShowRequestPermissionRationale(REQUEST_CODE_READ_PHONE_STATE)
    public void ShowRequestReadPhoneStatePermissionRationale() {
        DialogUtil.showPermissionDialog(mActivity, "\"读取手机状态\"", "s", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPermissions.requestPermissions(DeviceInfoFragment.this, REQUEST_CODE_READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);
            }
        });
    }*/
    @PermissionGrant(REQUEST_CODE_READ_PHONE_STATE)
    public void requestReadPhoneStateSuccess() {
        DialogUtil.showInfoDialog(mActivity, "本机ID", DeviceUtil.getIdInfo());
    }

    // TODO: 17/9/29  
    @PermissionDenied(REQUEST_CODE_READ_PHONE_STATE)
    public void requestReadPhoneStateFailed() {
        snackbar.show();
    }
}
