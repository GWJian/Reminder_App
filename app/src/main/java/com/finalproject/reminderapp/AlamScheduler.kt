package com.finalproject.reminderapp

import com.finalproject.reminderapp.data.model.AlarmItem


interface AlamScheduler {
    //schedule() is used to schedule the alarm to be delivered at the specified time.
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)

}