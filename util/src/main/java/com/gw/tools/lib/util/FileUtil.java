package com.gw.tools.lib.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by GongWen on 17/10/10.
 */

public class FileUtil {
    public static List<String> file2List(String filePath, String charsetName) {
        File file = new File(filePath);
        if (!file.isFile()) {
            return null;
        }
        Closeable closeable = null;
        List<String> arrayList = new ArrayList();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            closeable = bufferedReader;
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    arrayList.add(readLine);
                } else {
                    return arrayList;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (closeable != null) {
                CloseableCompat.close(closeable);
            }
        }
    }

    public static String formatFileSize(Context context, long sizeBytes) {
        return Formatter.formatFileSize(context, sizeBytes);
    }

    //存储相关
    public static boolean isExternalStorageMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    // TODO: 17/10/11 C0721y m558a
    public static long[] getInternalMemory(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager am = (ActivityManager) context.getSystemService(android.content.Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(memoryInfo);
        return new long[]{memoryInfo.totalMem, memoryInfo.availMem};
    }

    public static long[] getExternalStorage(Context context) {
        if (!isExternalStorageMounted()) {
            return new long[]{-1, -1};
        }
        try {
            long blocSize;
            long totalBlocks;
            long availableBlocks;
            StatFs statfs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blocSize = statfs.getBlockSizeLong();
                totalBlocks = statfs.getBlockCountLong();
                availableBlocks = statfs.getAvailableBlocksLong();
            } else {
                blocSize = statfs.getBlockSize();
                totalBlocks = statfs.getBlockCount();
                availableBlocks = statfs.getAvailableBlocks();
            }
            return new long[]{totalBlocks * blocSize, availableBlocks * blocSize};
        } catch (Exception e) {
            return new long[]{-1, -1};
        }
    }
}
