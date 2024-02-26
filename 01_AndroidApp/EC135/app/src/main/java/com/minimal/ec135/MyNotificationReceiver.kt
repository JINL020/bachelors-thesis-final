package com.minimal.ec135

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.minimal.ec135.MyNotificationService.Companion.NOTIFIER_KEY

class MyNotificationReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if(intent !=null) {
            val lp = intent.getStringExtra(NOTIFIER_KEY)
            val service = MyNotificationService(context)
            if (lp != null) {
                service.showNotification(lp)
            }
        }
    }
}