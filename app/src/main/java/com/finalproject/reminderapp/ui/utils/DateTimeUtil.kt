package com.finalproject.reminderapp.ui.utils

import com.finalproject.reminderapp.data.model.AlarmItem
import com.finalproject.reminderapp.ui.model.CustomDate
import com.finalproject.reminderapp.ui.model.CustomTime
import java.time.LocalDateTime
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

    fun String.getDate(): CustomDate? {
        val dateTemp = this.split("/")

        return if (dateTemp.size == 3) {
            CustomDate(
                dateTemp[2].toInt(),
                dateTemp[1].toInt(),
                dateTemp[0].toInt(),
            )
        } else {
            null
        }
    }

    fun String.getTime(): CustomTime? {
        val dateTime = this.split(":")

        return if (dateTime.size == 2) {
            CustomTime(
                dateTime[0].toInt(),
                dateTime[1].toInt(),
            )
        } else {
            null
        }
    }

}