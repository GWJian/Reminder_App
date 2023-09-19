package com.finalproject.reminderapp.data.repo

import com.finalproject.reminderapp.data.RemindersDao
import com.finalproject.reminderapp.data.model.Remind

import kotlinx.coroutines.flow.Flow

class RemindersRepo(
    private val dao: RemindersDao
) {
    fun getRemind(): Flow<List<Remind>> {
        return dao.getRemind()
    }

    fun addRemind(remind: Remind) {
        dao.insertRemind(remind)
    }

    fun deleteRemind(remind: Remind) {
        dao.deleteRemind(remind)
    }

    fun getRemindById(id: Int): Remind? {
        return dao.getRemindById(id)
    }


}