package com.gw.tools.lib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;

/**
 * Created by GongWen on 17/10/11.
 *  Android Intent Action 大全
 *  http://blog.csdn.net/ithomer/article/details/8242471
 *   Android开发笔记——Uri.parse的详细资料
 *  http://blog.csdn.net/tianchen28/article/details/17784457#comments
 */

public class IntentCompat {
    private IntentCompat() {
    }

    public static void openMiuiPermissionActivity(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + context.getPackageName()));
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 拨打电话
     * 需要权限<uses-permission android:name="android.permission.CALL_PHONE"/>
     *
     * @param context
     * @param uri     例如：Uri.parse("tel:xxx")
     */
    public static void actionCall(Context context, Uri uri) {
        context.startActivity(new Intent(Intent.ACTION_CALL, uri));
    }

    /**
     * 打开拨号面板
     *
     * @param context
     */
    public static void actionDial(Context context, Uri uri) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, uri));
    }

    public static void actionDial(Context context) {
        context.startActivity(new Intent(Intent.ACTION_DIAL));
    }

    // TODO: 17/10/11
    public static void actionAllApps(Context context) {
        context.startActivity(new Intent(Intent.ACTION_ALL_APPS));
    }

    // TODO: 17/10/11
    public static void actionAnswer(Context context) {
        context.startActivity(new Intent(Intent.ACTION_ANSWER));
    }

    // TODO: 17/10/11

    /**
     * @param context
     * @param uri     例如：
     *                打开网页：Uri.parse("http://www.baidu.com")
     *                打开地图：Uri.parse("geo:38.899533,-77.036476") 待验证
     *                播放音乐视频：Uri.parse("file:///sdcard/download/everything.mp3")待验证
     */
    public static void actionView(Context context, Uri uri) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    /**
     * 卸载应用
     *
     * @param context
     * @param packageName
     */
    public static void actionUninstall(Context context, String packageName) {
        Uri uri = Uri.fromParts("package", packageName, null);
        context.startActivity(new Intent(Intent.ACTION_DELETE, uri));
    }

    // TODO: 17/10/11
    public static void actionInstall(Context context, String packageName) {
        Uri uri = Uri.fromParts("package", packageName, null);
        context.startActivity(new Intent(Intent.ACTION_PACKAGE_ADDED, uri));
    }

    /**
     * 调起发邮件界面
     *
     * @param context
     * @param uri     例如：Uri.parse("mailto:xxxx@gmail.com")
     */
    public static void actionSendTo(Context context, Uri uri) {
        context.startActivity(new Intent(Intent.ACTION_SENDTO, uri));
    }

    /**
     * 拍照
     * @param context
     * @param requestCode
     */
    public static void actionImageCapture(Fragment context, int requestCode) {
        actionImageCapture(context, requestCode, null);
    }

    public static void actionImageCapture(Fragment context, int requestCode, Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (uri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        context.startActivityForResult(intent, requestCode);
    }

}
