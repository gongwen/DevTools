package com.gw.tools.lib.util;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by GongWen on 17/10/10.
 */

public class CollectionCompat {
    public static final CharSequence delimiter = ",";

    public static <V> int size(Collection<V> collection) {
        return collection == null ? 0 : collection.size();
    }

    public static <V> boolean isEmpty(Collection<V> collection) {
        return collection == null || collection.size() == 0;
    }

    public static <V> boolean isEmpty(V[] array) {
        return array == null || array.length == 0;
    }

    public static String join(Iterable tokens) {
        return join(delimiter, tokens);
    }

    public static String join(CharSequence delimiter, Iterable tokens) {
        return tokens == null ? null : TextUtils.join(delimiter, tokens);
    }

    public static String toString(Map map) {
        if (map == null) {
            return "null";
        }
        return Arrays.toString(map.entrySet().toArray());
    }

    public static String toString(List list) {
        if (list == null) {
            return "null";
        }
        return Arrays.toString(list.toArray());
    }
}
