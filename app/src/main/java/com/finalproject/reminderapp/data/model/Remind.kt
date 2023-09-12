package com.finalproject.reminderapp.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

//@Parcelize
@Entity
data class Remind(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val desc: String,
    val date: String,
    val time: String,
    //val isActive: Boolean = false, //to check is the alarm active or not
)
//    : Parcelable {
//Combine AlarmItem to Remind
{
    fun toAlarmItem(): AlarmItem? {
        val dateTemp = this.date.split("/")
        val dateTime = this.time.split(":")

        return if (dateTemp.size == 3 && dateTime.size == 2) {

            val localDateTime = LocalDateTime.of(
                dateTemp[2].toInt(), //year
                dateTemp[1].toInt(), //month
                dateTemp[0].toInt(), //day
                dateTime[0].toInt(), //hour
                dateTime[1].toInt(), //minute
                0
            )
            AlarmItem(
                localDateTime,
                this.title
            )
        } else null
    }
}



