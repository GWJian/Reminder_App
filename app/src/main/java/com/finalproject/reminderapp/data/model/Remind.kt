package com.finalproject.reminderapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Remind(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val desc: String,
    val date: String,
    val time: String,
    val isActive:Boolean = false //to check is the alarm active or not
)



