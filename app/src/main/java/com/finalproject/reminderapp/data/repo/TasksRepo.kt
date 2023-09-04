package com.finalproject.reminderapp.data.repo

import android.util.Log
import com.finalproject.reminderapp.data.TasksDao
import com.finalproject.reminderapp.data.model.Task
import kotlinx.coroutines.flow.Flow

class TasksRepo(
    private val dao: TasksDao
) {
    fun getTasks(): Flow<List<Task>> {
        return dao.getTasks()
    }

    fun addTask(task: Task) {
        dao.insertTask(task)
    }

    fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }

    fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)
    }


}