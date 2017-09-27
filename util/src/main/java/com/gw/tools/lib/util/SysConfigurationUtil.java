package com.gw.tools.lib.util;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class SysConfigurationUtil {
    private SysConfigurationUtil() {
    }

    public static Configuration getConfiguration(Context context) {
        if (context == null || context.getResources() == null) {
            return null;
        }
        return context.getResources().getConfiguration();
    }

    public static Locale getLocale(Context context) {
        Configuration configuration = getConfiguration(context);
        if (configuration == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return configuration.locale;
        }
        LocaleList locales = configuration.getLocales();
        if (locales == null || locales.size() <= 0) {
            return null;
        }
        return locales.get(0);
    }

    public static String getLanguage(Context context) {
        Locale locale = getLocale(context);
        return locale == null ? null : locale.getLanguage();
    }

    public static String getLanguageCountry(Context context) {
        Locale locale = getLocale(context);
        if (locale == null) {
            return null;
        }
        String country = locale.getCountry();
        if (TextUtils.isEmpty(country)) {
            return locale.getLanguage();
        }
        return locale.getLanguage() + "_" + country;
    }

    public static boolean m902e(Context context) {
        return "zh_cn".equalsIgnoreCase(getLanguageCountry(context));
    }

    public static int getColor(Context context, int i) {
        return ContextCompat.getColor(context, i);
    }

    public static String getString(Context context, int i) {
        return context.getString(i);
    }

    public static String getAssetsContent(Context context, String assetFilePath) {
        String result = null;
        if (context != null && !TextUtils.isEmpty(assetFilePath)) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open(assetFilePath)));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuilder.append(readLine);
                }
                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}