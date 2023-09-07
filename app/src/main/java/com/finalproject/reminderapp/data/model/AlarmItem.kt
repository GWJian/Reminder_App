package com.finalproject.reminderapp.data.model

import java.time.LocalDateTime

data class AlarmItem(
    val time : LocalDateTime,
    val message : String,
)