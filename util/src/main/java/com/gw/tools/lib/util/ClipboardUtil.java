package com.gw.tools.lib.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by GongWen on 17/9/28.
 * Android Clipboard 详解
 * http://www.jianshu.com/p/213d7062cdbe
 */

public class ClipboardUtil {
    private ClipboardUtil() {
    }

    public static void copy(Context context, CharSequence label, CharSequence text) {
        ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE))
                .setPrimaryClip(ClipData.newPlainText(label, text));
    }

    /*public static String paste(Context context) {
        String result = "";
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager.hasPrimaryClip()) {
            ClipData clipData = clipboardManager.getPrimaryClip();
            ClipDescription clipDescription = clipboardManager.getPrimaryClipDescription();
            String label = clipDescription.getLabel().toString();
            String text = clipData.getItemAt(0).getText().toString();
            result = String.format("label：%s,text：%s", label, text);
        }
        return result;
    }*/
}
