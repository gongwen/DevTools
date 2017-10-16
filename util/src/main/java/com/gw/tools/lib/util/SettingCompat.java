package com.gw.tools.lib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;

/**
 * Created by GongWen on 17/10/12.
 */

public class SettingCompat {
    private SettingCompat() {
    }

    public static void openAppDetailsSettings(Activity activity, int requestCode) {
        String action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
        Uri uri = Uri.parse("package:" + SysUtil.getPackageName(activity));
        activity.startActivityForResult(new Intent(action, uri), requestCode);
    }

    public static void openAppDetailsSettings(android.support.v4.app.Fragment fragment, int requestCode) {
        String action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
        Uri uri = Uri.parse("package:" + SysUtil.getPackageName(fragment.getContext()));
        fragment.startActivityForResult(new Intent(action, uri), requestCode);
    }

    public static void openAppDetailsSettings(android.app.Fragment fragment, int requestCode) {
        String action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
        Uri uri = Uri.parse("package:" + SysUtil.getPackageName(fragment.getActivity()));
        fragment.startActivityForResult(new Intent(action, uri), requestCode);
    }

    public static void openAppDetailsSettings(Context context) {
        String action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
        Uri uri = Uri.parse("package:" + SysUtil.getPackageName(context));
        ContextCompat.startActivity(context, new Intent(action, uri), null);
    }

    public static void goManageOverlayPermission(Fragment fragment, int requestCode) {
        String action = Settings.ACTION_MANAGE_OVERLAY_PERMISSION;
        Uri uri = Uri.parse("package:" + SysUtil.getPackageName(fragment.getContext()));
        fragment.startActivityForResult(new Intent(action, uri), requestCode);

    }

    // TODO: 17/10/12 待验证
    public static boolean canDrawOverlays(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }

    //辅助功能相关########################################################

    public static boolean checkAccessibility(Fragment fragment, int requestCode) {
        // 判断辅助功能是否开启
        if (!isAccessibilitySettingsOn(fragment.getActivity())) {
            // 引导至辅助功能设置页面
            fragment.startActivityForResult(
                    new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), requestCode);
            ToastUtil.shortToast(fragment.getActivity(), "请先开启 \"Activity 栈\" 的辅助功能");
            return false;
        }
        return true;
    }

    public static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }


    public static void openOtherApp(Context context, String packageName) {
        if (isAppInstalled(context, packageName)) {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            openMarketForPackageName(context, packageName);
        }
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            return context.getPackageManager().getApplicationInfo(packageName, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    //########################Android跳转系统界面########################

    /**
     * 跳转Setting应用列表（所有应用）
     *
     * @param context
     */
    // TODO: 17/10/16 待验证
    public static void openManagerAllAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 跳转Setting应用列表（安装应用）
     *
     * @param context
     */
    public static void openManagerInstalledAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 跳转Setting应用列表
     *
     * @param context
     */
    public static void openAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 开发者选项
     *
     * @param context
     */
    public static void openAppDevSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 允许在其它应用的上层显示的应用
     *
     * @param context
     */
    public static void openManageOverlayPermission(Context context, String packageName) {
        String action = Settings.ACTION_MANAGE_OVERLAY_PERMISSION;
        Uri uri = Uri.parse("package:" + packageName);
        context.startActivity(new Intent(action, uri));
        //activity/fragment.startActivityForResult(new Intent(action, uri), requestCode);
    }

    /**
     * 无障碍设置
     *
     * @param context
     */
    public static void openAccessibilitySettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }

    /**
     * 添加账户
     *
     * @param context
     */
    // TODO: 17/10/17 待研究
    public static void openAddAccount(Context context) {
        context.startActivity(new Intent(Settings.ACTION_ADD_ACCOUNT));
    }

    /**
     * 移动网络设置
     *
     * @param context
     */
    // TODO: 17/10/17 待研究
    public static void openDataRoamingSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
    }

    /**
     * WIFI设置
     *
     * @param context
     */
    public static void openWifiSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    /**
     * 蓝牙设置
     *
     * @param context
     */
    public static void openBluetoothSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
    }

    /**
     * 日期时间设置
     *
     * @param context
     */
    public static void openDateSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
    }

    /**
     * 关于手机界面
     *
     * @param context
     */
    public static void openDeviceInfoSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS));
    }

    /**
     * 设置－显示界面
     *
     * @param context
     */
    public static void openDisplaySettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
    }

    /**
     * 设置－声音和振动界面
     *
     * @param context
     */
    public static void openSoundSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_SOUND_SETTINGS));
    }

    /**
     * 互动屏保
     *
     * @param context
     */
    // TODO: 17/10/17 待研究
    public static void openDreamSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_DREAM_SETTINGS));
    }

    /**
     * 输入法
     *
     * @param context
     */
    public static void openInputMethodSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
    }

    /**
     * 输入法_SubType
     *
     * @param context
     */
    public static void openInputMethodSubtypeSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SUBTYPE_SETTINGS));
    }

    /**
     * 内部存储设置界面
     *
     * @param context
     */
    public static void openInternalStorageSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS));
    }

    /**
     * 存储卡设置界面
     *
     * @param context
     */
    public static void openMemoryCardSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_MEMORY_CARD_SETTINGS));
    }

    /**
     * 语言选择界面
     *
     * @param context
     */
    public static void openLocaleSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
    }

    /**
     * 设备安全性－开启位置服务界面
     *
     * @param context
     */
    public static void openLocationSourceSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    /**
     * 运营商
     *
     * @param context
     */
    public static void openNetworkOperatorSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
    }

    /**
     * NFC共享界面
     *
     * @param context
     */
    // TODO: 17/10/17 待验证
    public static void openNfcSharingSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
    }

    /**
     * NFC设置
     *
     * @param context
     */
    // TODO: 17/10/17 待验证
    public static void openNfcSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
    }

    /**
     * 备份和重置
     *
     * @param context
     */
    public static void openPrivacySettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_PRIVACY_SETTINGS));
    }

    /**
     * 快速启动
     *
     * @param context
     */
    // TODO: 17/10/17 崩溃 
    public static void openQuickLaunchSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_QUICK_LAUNCH_SETTINGS));
    }

    /**
     * 搜索设置
     *
     * @param context
     */
    public static void openSearchSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_SEARCH_SETTINGS));
    }

    /**
     * 安全设置
     *
     * @param context
     */
    public static void openSecuritySettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_SECURITY_SETTINGS));
    }

    /**
     * 设置的主页
     *
     * @param context
     */
    public static void openSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
    }

    /**
     * 用户同步界面
     *
     * @param context
     */
    public static void openSyncSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_SYNC_SETTINGS));
    }

    /**
     * 用户字典
     *
     * @param context
     */
    public static void openUserDictionarySettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_USER_DICTIONARY_SETTINGS));
    }

    /**
     * IP设置
     *
     * @param context
     */
    public static void openWifiIpSettings(Context context) {
        context.startActivity(new Intent(Settings.ACTION_WIFI_IP_SETTINGS));
    }

    /**
     * App应用信息界面
     *
     * @param context
     */
    public static void openAppDetailsSettings(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(packageURI);
        context.startActivity(intent);
    }

    /**
     * 跳转应用市场
     *
     * @param context
     * @param packageName
     */
    public static void openMarketForPackageName(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + packageName));
        context.startActivity(intent);
    }

    /**
     * 跳转应用市场
     *
     * @param context
     * @param appName
     */
    public static void openMarketForAppName(Context context, String appName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://search?q=" + appName));
        context.startActivity(intent);
    }

    /**
     * 获取Launcher包名
     *
     * @param context
     */
    // TODO: 17/10/17 待研究
    public static void getLauncherPackageName(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        if (res.activityInfo == null) {
            Log.e("TAG", "没有获取到");
            return;
        }

        if (res.activityInfo.packageName.equals("android")) {
            Log.e("TAG", "有多个Launcher，且未指定默认");
        } else {
            Log.e("TAG", res.activityInfo.packageName);
        }
    }

    /**
     * 跳转图库获取图片
     *
     * @param activity
     * @param requestCode
     */
    // TODO: 17/10/17 待研究
    public static void openGallery(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转相机，拍照并保存
     *
     * @param activity
     * @param requestCode
     */
    // TODO: 17/10/17 待研究
    public static void openCamera(Activity activity, File savePath, int requestCode) {
        Uri headCacheUri = Uri.fromFile(savePath);
        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, headCacheUri);
        activity.startActivityForResult(takePicIntent, requestCode);
    }

    /**
     * 跳转相机，拍照并保存
     *
     * @param activity
     * @param requestCode
     */
    // TODO: 17/10/17 待研究
    public static void openFileManager(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("file/*");
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 直接拨打电话
     *
     * @param activity
     * @param tel
     */
    public static void openCall(Activity activity, String tel) {
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
        activity.startActivity(callIntent);
    }

    /**
     * 跳转电话应用
     *
     * @param activity
     * @param tel
     */
    public static void openDial(Activity activity, String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * 发送短信
     *
     * @param activity
     * @param tel
     */
    public static void openSms(Activity activity, String tel) {
        Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO, Uri.parse("smsto:" + tel));
        activity.startActivity(mIntent);
    }

    /**
     * 发送彩信
     *
     * @param activity
     * @param tel
     */
    // TODO: 17/10/17 待研究
    public static void openMultiMediaSms(Activity activity, String tel) {
        Uri uri = Uri.parse("content://media/external/images/media/11");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra("sms_body", "some text");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/png");
        activity.startActivity(intent);
    }

    /**
     * 发送邮件
     *
     * @param activity
     * @param fromEmail
     * @param toEmail
     */
    // TODO: 17/10/17 待研究
    public static void openSendEmail(Activity activity, String[] fromEmail, String toEmail) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + toEmail));
        intent.putExtra(Intent.EXTRA_CC, fromEmail); // 抄送人
        intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文
        activity.startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
    }

    /**
     * 跳转联系人
     *
     * @param context
     */
    // TODO: 17/10/17  
    public static void openContact(Context context) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Contacts.People.CONTENT_URI));

    /*Intent intent = new Intent();
    intent.setAction(Intent.ACTION_PICK);
    intent.setData(Uri.parse("content://contacts/people"));
    startActivityForResult(intent, 5);*/
    }

    /**
     * 插入联系人
     *
     * @param activity
     * @param tel
     * @param requestCode
     */
    public static void insertContact(Activity activity, String name, String tel, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(ContactsContract.Contacts.CONTENT_URI)
                .putExtra(ContactsContract.Intents.Insert.NAME, name)
                .putExtra(ContactsContract.Intents.Insert.PHONE, tel);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 插入日历事件
     *
     * @param activity
     * @param title
     * @param requestCode
     */
    public static void insertCalenderEvent(Activity activity, String title, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, title);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转浏览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    /**
     * 安装应用
     *
     * @param context
     * @param appFilePath
     */
    // TODO: 17/10/17 待研究
    public static void goInstallApp(Context context, String appFilePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + appFilePath), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 卸载应用
     *
     * @param context
     * @param packageName
     */
    // TODO: 17/10/17 待研究
    public static void goUnInstallApp(Context context, String packageName) {
        context.startActivity(new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName)));
    }

    /**
     * 回到桌面
     *
     * @param context
     */
    public static void openLauncherHome(Context context) {
        context.startActivity(new Intent(Intent.ACTION_VIEW).addCategory(Intent.CATEGORY_HOME));
    }

    /**
     * 跳转录音
     *
     * @param context
     */
    public static void openRecord(Context context) {
        context.startActivity(new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION));
    }

    /**
     * 打开任意文件（根据其MIME TYPE自动选择打开的应用）
     *
     * @param file
     */
    // TODO: 17/10/17  
    public static void openFile(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), FileUtil.getMIMEType(file));
        context.startActivity(intent);
    }

    //########################Android跳转系统界面########################

}
