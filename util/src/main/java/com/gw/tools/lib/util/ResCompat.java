package com.gw.tools.lib.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;

/**
 * Created by GongWen on 17/9/27.
 */

public class ResCompat {
    private ResCompat() {
    }

    public static Resources getSystemResources() {
        return Resources.getSystem();
    }

    public static Resources getResources(Context context) {
        return context.getResources();
    }

    public static int getColor(Context mContext, @ColorRes int id) {
        return getColor(mContext, id, mContext.getTheme());
    }

    public static ColorStateList getColorStateList(Context mContext, @ColorRes int id) {
        return getColorStateList(mContext, id, mContext.getTheme());
    }

    public static Drawable getDrawable(Context mContext, @ColorRes int id) {
        return getDrawable(mContext, id, mContext.getTheme());
    }

    public static Drawable getDrawableForDensity(Context mContext, @ColorRes int id, int density) {
        return getDrawableForDensity(mContext, id, density, mContext.getTheme());
    }

    public static int getColor(Context mContext, @ColorRes int id, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getColor(mContext.getResources(), id, theme);
    }

    public static ColorStateList getColorStateList(Context mContext, @ColorRes int id, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getColorStateList(mContext.getResources(), id, theme);
    }

    public static Drawable getDrawable(Context mContext, @DrawableRes int id, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getDrawable(mContext.getResources(), id, theme);
    }

    public static Drawable getDrawableForDensity(Context mContext, @DrawableRes int id, int density, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getDrawableForDensity(mContext.getResources(), id, density, theme);
    }
}
