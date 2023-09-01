package com.finalproject.reminderapp

import android.app.Application
import androidx.room.Room
import com.finalproject.reminderapp.data.TaskDataBase
import com.finalproject.reminderapp.data.repo.TasksRepo

class MyApplication : Application() {

    lateinit var tasksRepo: TasksRepo

    override fun onCreate() {
        super.onCreate()

        val tasksDataBase = Room.databaseBuilder(
            this,
            TaskDataBase::class.java,
            TaskDataBase.name
        )
            .addMigrations(TaskDataBase.MIGRATION_1_2)
            .build()

        tasksRepo = TasksRepo(tasksDataBase.dao)
    }

}