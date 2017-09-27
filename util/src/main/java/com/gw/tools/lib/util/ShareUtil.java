package com.gw.tools.lib.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by GongWen on 17/9/28.
 */

public class ShareUtil {
    private ShareUtil() {
    }

    public static void shareText(Context context, String text) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        context.startActivity(intent);
    }
    /*public static void shareImage(Context context, Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image*//*");
        context.startActivity(intent);
    }

    private void shareMultiImage(Context context, ArrayList<Uri> uris) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_STREAM, uris);
        intent.setType("image*//*");
        context.startActivity(intent);
    }*/
}
