package com.gw.tools;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gw.tools.fragment.MainFragment;

/**
 *
 */
public class MainActivity extends BaseActivity {
    @Override
    int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(new MainFragment());
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
