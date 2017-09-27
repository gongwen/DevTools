package com.gw.tools.lib.util;

import android.util.SparseArray;

/**
 * Created by GongWen on 17/10/10.
 */

public class SysVersionNameUtil {
    private static SparseArray<String> versionNameMap;

    private SysVersionNameUtil() {
    }

    // TODO: 17/10/10 不断更新中 
    public static String getSysVersionName(int version) {
        if (versionNameMap == null) {
            synchronized (SysVersionNameUtil.class) {
                if (versionNameMap == null) {
                    versionNameMap = new SparseArray();
                    versionNameMap.put(1, "Alpha");
                    versionNameMap.put(2, "Beta");
                    versionNameMap.put(3, "Cupcake");
                    versionNameMap.put(4, "Donut");
                    versionNameMap.put(5, "Eclair");
                    versionNameMap.put(6, "Eclair");
                    versionNameMap.put(7, "Eclair MR1");
                    versionNameMap.put(8, "Froyo");
                    versionNameMap.put(9, "Gingerbread");
                    versionNameMap.put(10, "Gingerbread MR1");
                    versionNameMap.put(11, "Honeycomb");
                    versionNameMap.put(12, "Honeycomb MR1");
                    versionNameMap.put(13, "Honeycomb MR2");
                    versionNameMap.put(14, "Ice Cream Sandwich");
                    versionNameMap.put(15, "Ice Cream Sandwich MR1");
                    versionNameMap.put(16, "Jelly Bean");
                    versionNameMap.put(17, "Jelly Bean MR1");
                    versionNameMap.put(18, "Jelly Bean MR2");
                    versionNameMap.put(19, "KitKat");
                    versionNameMap.put(20, "KitKat for watches");
                    versionNameMap.put(21, "Lollipop");
                    versionNameMap.put(22, "Lollipop MR1");
                    versionNameMap.put(23, "Marshmallow");
                    versionNameMap.put(24, "Nougat");
                    versionNameMap.put(25, "Nougat");
                    versionNameMap.put(26, "Oreo");
                }
            }
        }
        return versionNameMap.get(version);
    }
}