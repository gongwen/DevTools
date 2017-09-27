package com.gw.tools.lib.util;

import java.io.Closeable;

/**
 * Created by GongWen on 17/10/10.
 */

public class CloseableCompat {
    private CloseableCompat() {
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                throw new RuntimeException("IOException occurred. ", e);
            }
        }
    }
}
