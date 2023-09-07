package com.finalproject.reminderapp.data.repo

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.finalproject.reminderapp.AlamReceiver
import com.finalproject.reminderapp.AlamScheduler
import com.finalproject.reminderapp.data.model.AlarmItem
import java.time.ZoneId

class AndroidAlarmScheduler(
    private val context: Context
) : AlamScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    //schedule() is used to schedule the alarm to be delivered at the specified time.
    override fun schedule(alarmItem: AlarmItem) {
        val intent = Intent(context, AlamReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", alarmItem.message)
        }

        //The time is converted to milliseconds and then passed to the alarmManager
        val t = alarmItem.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
        Log.d("AlarmClockTrigger", "$t")

        //The alarm is scheduled to be delivered at the specified time.
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmItem.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                //with this hashCode,it will be unique for every alarmItem
                alarmItem.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(alarmItem: AlarmItem) {
        Log.d("AlarmClockTrigger","Alarm cancel")
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmItem.hashCode(),
                Intent(context, AlamReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}