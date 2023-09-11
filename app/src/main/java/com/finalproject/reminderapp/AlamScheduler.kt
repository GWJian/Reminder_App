package com.finalproject.reminderapp

import com.finalproject.reminderapp.data.model.AlarmItem


interface AlamScheduler {
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}