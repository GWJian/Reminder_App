package com.finalproject.reminderapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.finalproject.reminderapp.data.model.Remind
import kotlinx.coroutines.flow.Flow

@Dao
interface RemindersDao {

    @Query("SELECT * FROM remind")
    fun getRemind(): Flow<List<Remind>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRemind(remind: Remind)

    @Delete
    fun deleteRemind(remind: Remind)

    @Query("SELECT * FROM remind WHERE id = :id")
    fun getRemindById(id: Int): Remind?



}