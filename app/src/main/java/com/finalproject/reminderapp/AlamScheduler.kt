package com.finalproject.reminderapp

import com.finalproject.reminderapp.data.model.AlarmItem
import com.finalproject.reminderapp.data.model.Remind


interface AlamScheduler {
    //schedule() is used to schedule the alarm to be delivered at the specified time.
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: Remind)
}