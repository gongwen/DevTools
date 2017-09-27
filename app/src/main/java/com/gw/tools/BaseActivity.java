package com.gw.tools;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.gw.tools.lib.util.ResCompat;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by GongWen on 17/9/27.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected DisplayMetrics displayMetrics;
    private Unbinder unbinder;

    abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ResCompat.getColor(this, R.color.colorTheme), 0);
        displayMetrics = getResources().getDisplayMetrics();
    }

    public void addFragment(Fragment fragment) {
        if (fragment != null) {
            addFragment(fragment, fragment.getClass().getSimpleName());
        }
    }

    public void addFragment(Fragment fragment, String tag) {
        if (null != fragment) {
            Fragment lastFragment = getLastFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (lastFragment != null) {
                fragmentTransaction.hide(lastFragment);
            }
            fragmentTransaction.add(R.id.fragmentContainer, fragment, tag)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void showFragment(Fragment fragment) {
        if (null != fragment) {
            Fragment lastFragment = getLastFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (lastFragment != null) {
                fragmentTransaction.hide(lastFragment);
            }
            fragmentTransaction.show(fragment).commit();
        }
    }

    public Fragment findFragmentById(@IdRes int id) {
        return getSupportFragmentManager().findFragmentById(id);
    }

    public Fragment findFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    // TODO: 17/9/28
    public void removeFragment(Fragment fragment) {
        if (null != fragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
        }
    }

    // TODO: 17/9/28
    public void removeAllFragment() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragmentList) {
            fragmentTransaction.remove(fragment);
        }
    }

    // TODO: 17/9/28
    public void replaceFragment(Fragment fragment, String tag) {
        if (null != fragment) {
            Fragment lastFragment = getLastFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (lastFragment != null) {
                fragmentTransaction.hide(lastFragment);
            }
            fragmentTransaction.replace(R.id.fragmentContainer, fragment, tag).commit();
        }
    }

    public List<Fragment> getFragments() {
        return getSupportFragmentManager().getFragments();
    }

    public Fragment getLastFragment() {
        List<Fragment> fragments = getFragments();
        return fragments.size() == 0 ? null : fragments.get(fragments.size() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
