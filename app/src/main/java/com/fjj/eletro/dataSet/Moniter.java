package com.fjj.eletro.dataSet;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.NotificationUtils;
import com.fjj.eletro.R;
import com.fjj.eletro.broadcast.NotificationActionClickReceiver;

public class Moniter {
    public static void TriggerMoniterPower(Context context, double threshold) {
        Power power = ParserJson.getDataSet().getPower();
        for (Power_detail item: power.getDetail()) {
            if(item.getPower() > threshold) {
                if(NotificationUtils.areNotificationsEnabled()) {
                    NotificationUtils.notify(GetId(item), builder -> {
                        Intent intent = new Intent(context, NotificationActionClickReceiver.class);
                        intent.setAction(context.getString(R.string.notification_threshold_intent_action));
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent.putExtra(context.getString(R.string.notification_threshold_intent_param_dormitory_no), item.getName());
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(context.getString(R.string.notification_threshold_title_format, item.getName()))
                                .setContentText(context.getString(R.string.notification_threshold_context_format, item.getPower()))
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);
                    });
                }
            }
        }
    }

    public static int GetId(Power_detail item) {
        return Integer.parseInt(item.getName().replace("S-", "9465").replace("N-", "9466"));
    }
}