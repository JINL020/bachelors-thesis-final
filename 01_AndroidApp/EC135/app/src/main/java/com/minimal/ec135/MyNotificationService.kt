package com.minimal.ec135

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat

class MyNotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(lp: String) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val extras = Bundle()
        extras.putString(NOTIFIER_KEY, lp);
        activityIntent.putExtras(extras);

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT else 0
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_back_hand_filled)
            .setContentTitle("Found licence plate $lp")
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }

    companion object {
        const val CHANNEL_ID = "notification_channel"
        const val NOTIFIER_KEY = "notification_detection"
    }
}