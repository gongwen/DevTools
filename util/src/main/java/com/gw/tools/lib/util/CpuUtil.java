package com.gw.tools.lib.util;

import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by GongWen on 17/10/10.
 */

public class CpuUtil {
    private CpuUtil() {
    }

    private static Map<String, String> cpuInfoMap;

    public static Map<String, String> getCpuInfo() {
        if (cpuInfoMap == null) {
            cpuInfoMap = new HashMap<>();
            List<String> cpuInfoList = FileUtil.file2List("/proc/cpuinfo", "utf-8");
            for (String item : cpuInfoList) {
                if (TextUtils.isEmpty(item)) {
                    continue;
                }
                String[] keyValue = TextUtils.split(item, ":");
                cpuInfoMap.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return cpuInfoMap;
    }

    public static String getCpuHardwareInfo() {
        cpuInfoMap = getCpuInfo();
        return cpuInfoMap.get("Hardware");
    }

    public static String getSupportABIs() {
        String ABIs;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ABIs = TextUtils.join(", ", Build.SUPPORTED_ABIS);
        } else {
            ABIs = TextUtils.join(", ", new String[]{Build.CPU_ABI, Build.CPU_ABI2});
        }
        return ABIs;
    }

    /**
     * https://stackoverflow.com/questions/30119604/how-to-get-the-number-of-cores-of-an-android-device
     *
     * @return
     */
    public static int getNumberOfCores() {
        //refer to trinea
        return Runtime.getRuntime().availableProcessors();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Runtime.getRuntime().availableProcessors();
        } else {
            // Use saurabh64's answer
            return getNumCoresOldPhones();
        }*/
    }

    private static int getNumCoresOldPhones() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }
        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            //Default to return 1 core
            return 1;
        }
    }

    /**
     * https://stackoverflow.com/questions/32452924/how-to-detect-android-device-is-64-bit-or-32-bit-processor
     *
     * @return
     */
    public static String getSupportCPUBit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return TextUtils.join(", ", Build.SUPPORTED_ABIS).contains("64") ? "64-Bit" : "32-Bit";
        }
        return "32-Bit";
    }
}
