package com.fjj.eletro.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fjj.eletro.R
import com.fjj.eletro.activity.MainActivity

class NotificationActionClickReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            context.getString(R.string.notification_threshold_intent_action) -> {
                if (MainActivity.getInstance() == null) bootMainActivity(context, intent)
                else if (intent.hasExtra(context.getString(R.string.notification_threshold_intent_param_dormitory_no))) MainActivity.getInstance().notificationActionClickEvent(intent.getStringExtra(context.getString(R.string.notification_threshold_intent_param_dormitory_no)))
            }
        }
    }

    private fun bootMainActivity(context: Context, intent: Intent) {
        val bootIntent = Intent(context, MainActivity::class.java)
        bootIntent.action = context.getString(R.string.notification_threshold_intent_action)
        bootIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        bootIntent.putExtra(context.getString(R.string.notification_threshold_intent_param_dormitory_no), intent.getStringExtra(context.getString(R.string.notification_threshold_intent_param_dormitory_no)))
        context.startActivity(bootIntent)
    }
}