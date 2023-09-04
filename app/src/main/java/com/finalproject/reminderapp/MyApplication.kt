package com.finalproject.reminderapp

import android.app.Application
import androidx.room.Room
import com.finalproject.reminderapp.data.ReminderDataBase
import com.finalproject.reminderapp.data.repo.RemindersRepo

class MyApplication : Application() {
    lateinit var remindersRepo: RemindersRepo

    override fun onCreate() {
        super.onCreate()

        val remindDataBase = Room.databaseBuilder(
            this,
            ReminderDataBase::class.java,
            ReminderDataBase.name
        )
            .addMigrations(ReminderDataBase.MIGRATION_1_2)
            .build()

        remindersRepo = RemindersRepo(remindDataBase.dao)
    }
}
