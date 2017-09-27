package com.gw.tools.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.gw.tools.MainApplication;
import com.gw.tools.lib.util.CpuUtil;
import com.gw.tools.lib.util.FileUtil;
import com.gw.tools.lib.util.NetUtil;
import com.gw.tools.lib.util.NumberUtil;
import com.gw.tools.lib.util.ScreenUtil;
import com.gw.tools.lib.util.SysVersionNameUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by GongWen on 17/9/28.
 */

public class DeviceUtil {
    public DeviceUtil() {
    }

    // TODO: 17/10/11 C0593g.m481f
    public static String getScreenInfo() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("密度：%s dp / %s / %sx\n", dm.densityDpi, ScreenUtil.getDpiDesc(MainApplication.getInstance()), dm.density))
                .append(String.format("精确密度：%s x %s dp\n", dm.xdpi, dm.ydpi))
                .append(String.format("屏幕分辨率：%s x %s px\n", dm.widthPixels, dm.heightPixels))
                .append(getScreenPhysicalSize());
        return sb.toString();
    }

    // TODO: 17/10/11  C0598c.m483h
    public static String getSystemInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Android %s / %s / API %d\n", Build.VERSION.RELEASE, SysVersionNameUtil.getSysVersionName(Build.VERSION.SDK_INT), Build.VERSION.SDK_INT))
                .append(String.format("基带版本：%s\n", Build.getRadioVersion()))
                .append(String.format("编译版本号：%s\n", Build.DISPLAY))
                .append(String.format("Linux 内核版本：%s\n", System.getProperty("os.version")))
                .append(String.format("Http User Agent：%s", System.getProperty("http.agent")));
        return sb.toString();
    }

    public static String getHardwareInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("型号：%s\n", Build.MODEL))
                .append(String.format("制造商：%s\n", Build.MANUFACTURER))
                .append(String.format("主板：%s\n", Build.BOARD))
                .append(String.format("设备：%s\n", Build.DEVICE))
                .append(String.format("产品：%s", Build.PRODUCT));
        return sb.toString();
    }

    public static String getCPUInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("ABIs：%s\n", CpuUtil.getSupportABIs()))
                .append(String.format("CPU 核数：%s\n", CpuUtil.getNumberOfCores()))
                .append(String.format("CPU 位数：%s\n", CpuUtil.getSupportCPUBit()))
                .append(String.format("CPU 型号：%s\n", CpuUtil.getCpuHardwareInfo()));
        for (String key : CpuUtil.getCpuInfo().keySet()) {
            if ("Hardware".equals(key)) continue;
            sb.append(String.format("%s：%s\n", key, CpuUtil.getCpuInfo().get(key)));
        }
        return sb.toString().trim();
    }

    // TODO: 17/9/29 内存卡
    public static String getStorageInfo() {
        Context context = MainApplication.getInstance();
        StringBuilder sb = new StringBuilder();
        long[] internalMemory = FileUtil.getInternalMemory(context);
        long[] externalStorage = FileUtil.getExternalStorage(context);
        sb.append(String.format("内存：共 %s，%s 可用\n",
                FileUtil.formatFileSize(context, internalMemory[0]),
                FileUtil.formatFileSize(context, internalMemory[1])))
                .append(String.format("存储：共 %s，%s 可用",
                        FileUtil.formatFileSize(context, externalStorage[0]),
                        FileUtil.formatFileSize(context, externalStorage[1])));
        return sb.toString();
    }

    /**
     * IMEI(International Mobile Equipment Identity)
     * 是国际移动设备身份码的缩写，国际移动装备辨识码，是由15位数字组成的"电子串号"，
     * 它与每台手机一一对应，而且该码是全世界唯一的。每一只手机在组装完成后都将被赋予一个
     * 全球唯一的一组号码，这个号码从生产到交付使用都将被制造生产的厂商所记录。
     * <p>
     * <p>
     * （IMSI：International Mobile SubscriberIdentification Number）
     * 是国际移动用户识别码的缩写，是区别移动用户的标志，储存在SIM卡中，可用于区别移动用户的有效信息。
     * 其总长度不超过15位，同样使用0～9的数字，结构为：MCC+MNC+MSIN。其中MCC是移动用户所属国家代号，占3位数字，
     * 中国的MCC规定为460；MNC是移动网号码，最多由两位数字组成，用于识别移动用户所归属的移动通信网；
     * MSIN是移动用户识别码，用以识别某一移动通信网中的移动用户。
     * <p>
     * <p>
     * ICCID：Integrate circuit card identity 集成电路卡识别码（固化在手机SIM卡中）
     * ICCID为IC卡的唯一识别号码，共有20位数字组成，其编码格式为：XXXXXX 0MFSS YYGXX XXXXX。
     * 分别介绍如下： 前六位运营商代码：中国移动的为：898600；中国联通的为：898601。
     * <p>
     * <p>
     * IMSI：国际移动用户号码标识；
     * MSISDN:mobile subscriber ISDN用户号码，这个是我们说的139，136那个号码，说白了就是手机号；
     * ICCID:ICC identity集成电路卡标识，这个是唯一标识一张卡片物理号码的；
     * IMEI：international mobile Equipment identity手机唯一标识码；
     * <p>
     * 需要权限：<uses-permission android:name="android.permission.READ_PHONE_STATE" />
     */
    // TODO: 17/9/29 获取手机号方案
    public static String getIdInfo() {
        Context context = MainApplication.getInstance().getApplicationContext();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("手机号：%s\n", telephonyManager.getLine1Number()))
                .append(String.format("IMEI：%s\n", telephonyManager.getDeviceId()))
                .append(String.format("AndroidId：%s\n", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)))
                .append(String.format("Mac 地址：%s\n", NetUtil.getMacAddress((WifiManager) context.getSystemService(WIFI_SERVICE))))
                .append(String.format("ICCID：%s\n", telephonyManager.getSimSerialNumber()))
                .append(String.format("IMSI：%s\n", telephonyManager.getSubscriberId()))
                .append(String.format("SV：%s", telephonyManager.getDeviceSoftwareVersion()));
        return sb.toString();
    }

    // TODO: 17/9/29 测试vpn，IPV6等，断网显示
    public static String getNetInfo() {
        Context context = MainApplication.getInstance().getApplicationContext();
        StringBuilder sb = new StringBuilder();
        if (NetUtil.isNetConnected(MainApplication.getInstance())) {
            if (NetUtil.isWifiConnected(MainApplication.getInstance())) {
                sb.append(String.format("网络：%s\n", "Wifi"));
                WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                sb.append(String.format("Wifi SSID：%s\n", wifiInfo.getSSID()));
            } else if (NetUtil.isMobileConnected(MainApplication.getInstance())) {
                sb.append(String.format("网络：%s\n", "Mobile 4g/3g"));
            } else {
                sb.append(String.format("网络：%s\n", "UNKOWN"));
            }
        } else {
            sb.append(String.format("网络：%s\n", "disconnect"));
        }
        sb.append(String.format("IPV4：%s\n", NetUtil.getIPAddress(true).toString()))
                .append(String.format("IPV6：%s\n", NetUtil.getIPAddress(false).toString()))
                .append(String.format("Mac 地址：%s", NetUtil.getMacAddress((WifiManager) context.getSystemService(WIFI_SERVICE))));
        return sb.toString();
    }

    public static String getVMInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(isArtInUse() ? "ART" : "Dalvik")
                .append("\n")
                .append("VM Version：" + System.getProperty("java.vm.version") + "\n")
                .append("Java Home：" + System.getProperty("java.home") + "\n")
                .append("Class path：" + System.getProperty("java.class.path") + "\n")
                .append("Boot Class Path：" + System.getProperty("java.boot.class.path"));
        return sb.toString();
    }

    public static String getBuildInfo() {
        StringBuilder sb = new StringBuilder();
        Class buildClass = Build.class;
        Field[] fields = buildClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                sb.append(field.getName() + ": " + object2String(field.get(buildClass)) + "\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().trim();
    }

    public static String getBuildVersionInfo() {
        StringBuilder sb = new StringBuilder();
        Class buildVersionClass = Build.VERSION.class;
        Field[] fields = buildVersionClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                sb.append(field.getName() + ": " + object2String(field.get(buildVersionClass)) + "\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().trim();
    }

    public static String getSystemPropertiesInfo() {
        StringBuilder sb = new StringBuilder();
        Properties properties = System.getProperties();
        for (Object t : properties.keySet()) {
            sb.append(String.format("%s: %s \n", t.toString(), properties.getProperty(t.toString())));
        }
        Enumeration enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement().toString();
            sb.append(key + ": " + System.getProperty(key) + "\n");
        }
        return sb.toString().trim();
    }

    public static String getSystemEnvInfo() {
        StringBuilder sb = new StringBuilder();
        for (String key : System.getenv().keySet()) {
            sb.append(key + ": " + System.getenv().get(key) + "\n");
        }
        return sb.toString().trim();
    }

    private static String getScreenPhysicalSize() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        float xPPI = dm.widthPixels / dm.xdpi;
        float yPPI = dm.heightPixels / dm.ydpi;
        double screenInches = Math.sqrt(Math.pow(xPPI, 2) + Math.pow(yPPI, 2));
        return String.format("屏幕尺寸：%s\" x %s\" / %s 英寸",
                NumberUtil.formatDotForNumber(xPPI, 1),
                NumberUtil.formatDotForNumber(yPPI, 1),
                NumberUtil.formatDotForNumber(screenInches, 1));
    }

    private static boolean isArtInUse() {
        final String vmVersion = System.getProperty("java.vm.version");
        return !TextUtils.isEmpty(vmVersion) && vmVersion.startsWith("2");
    }

    private static String object2String(Object obj) {
        if (obj == null) {
            return "null";
        } else if (obj.getClass().isArray()) {
            return Arrays.toString((Object[]) obj);
        } else {
            return obj.toString();
        }
    }
}
