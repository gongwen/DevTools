package com.gw.tools.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.gw.tools.activitytracker.R;
import com.gw.tools.activitytracker.ViewHierarchicalActivity;

/**
 * Created by GongWen on 17/10/13.
 * <p>
 * 官方文档
 * https://developer.android.com/guide/topics/ui/notifiers/notifications.html?hl=zh-cn
 */

public class NotificationUtil {
    private NotificationUtil() {
    }

    // TODO: 17/10/16 通知图标设置，点击通知Activity返回问题
    public static void showActivityTrackerNotification(Context context, String packageName, String className, int requestCode) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, null)
                .setSmallIcon(R.drawable.close)
                .setContentTitle(packageName)
                .setContentText(className);

        Class targetClass = ViewHierarchicalActivity.class;
        Intent resultIntent = new Intent(context, targetClass);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(targetClass);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                requestCode,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(CommonPool.ACTIVITY_TRACKER_NOTIFICATION_ID, builder.build());
    }
}
