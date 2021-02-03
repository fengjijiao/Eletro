package com.fjj.eletro.dataSet;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.NotificationUtils;
import com.fjj.eletro.R;

public class Moniter {
    public static void TriggerMoniterPower(Context context, double threshold) {
        Power power = ParserJson.getDataSet().getPower();
        for (Power_detail item: power.getDetail()) {
            if(item.getPower() > threshold) {
                if(NotificationUtils.areNotificationsEnabled()) {
                    NotificationUtils.notify(GetId(item), builder -> {
                        Intent intent = new Intent().putExtra("id", GetId(item));
                        builder.setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(String.format("%s超过警报阈值！！！", item.getName()))
                                .setContentText(String.format("当前功率：%.2f kwh\n\n于：%s ~ %s", item.getPower(), item.getStart_time(), item.getEnd_time()))
                                .setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
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