package com.gw.tools.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.gw.tools.MainApplication;
import com.gw.tools.R;
import com.gw.tools.lib.util.ClipboardUtil;
import com.gw.tools.lib.util.ShareUtil;
import com.gw.tools.lib.util.ToastUtil;

/**
 * Created by GongWen on 17/9/28.
 */

public class DialogUtil {
    private DialogUtil() {
    }

    public static AlertDialog showInfoDialog(Context context, String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(R.layout.dialog_info)
                .setCancelable(true);
        AlertDialog dialog = builder.show();
        float density = context.getResources().getDisplayMetrics().density;
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.TRANSPARENT);
        gd.setCornerRadius(5 * density);
        InsetDrawable inset = new InsetDrawable(gd, (int) (35 * density));
        dialog.getWindow().setBackgroundDrawable(inset);
        ((TextView) dialog.findViewById(R.id.titleView)).setText(title);
        ((TextView) dialog.findViewById(R.id.contentView)).setText(content);
        dialog.findViewById(R.id.shareView).setOnClickListener(v -> ShareUtil.shareText(context, content));
        dialog.findViewById(R.id.copyView).setOnClickListener(v -> {
            dialog.dismiss();
            ClipboardUtil.copy(MainApplication.getInstance(),"this is a label!", content);
            ToastUtil.shortToast(MainApplication.getInstance(),"复制成功");
        });

        return dialog;
    }

    public static AlertDialog showYesNoDialog(Context context, String title, String content, View.OnClickListener confirmClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(R.layout.dialog_yes_no)
                .setCancelable(true);
        AlertDialog dialog = builder.show();
        ((TextView) dialog.findViewById(R.id.titleView)).setText(title);
        ((TextView) dialog.findViewById(R.id.contentView)).setText(content);
        dialog.findViewById(R.id.cancelView).setOnClickListener(v -> dialog.dismiss());
        dialog.findViewById(R.id.confirmView).setOnClickListener(v -> {
            dialog.dismiss();
            if (confirmClickListener != null) {
                confirmClickListener.onClick(v);
            }
        });

        return dialog;
    }

    public static AlertDialog showPermissionDialog(Context context, String permissionDesc, View.OnClickListener confirmClickListener) {
        return DialogUtil.showYesNoDialog(context, "权限申请说明",
                String.format("我们需要%s权限来展示数据，我们保证不收集任何数据", permissionDesc), confirmClickListener);
    }
}
