package com.finalproject.reminderapp.ui.utils

import com.finalproject.reminderapp.ui.model.CustomDate
import com.finalproject.reminderapp.ui.model.CustomTime
import java.util.Calendar

object DateTimeUtil {

    fun getDate(): CustomDate {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        return CustomDate(year, month, dayOfMonth)
    }

    fun getTime(): CustomTime {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return CustomTime(hour, minute)
    }

}