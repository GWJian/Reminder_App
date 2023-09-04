package com.finalproject.reminderapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.finalproject.reminderapp.data.model.Remind

@Database(entities = [Remind::class], version = 2)
abstract class ReminderDataBase : RoomDatabase() {

    abstract val dao: RemindersDao

    companion object {
        const val name = "reminder_database"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    ALTER TABLE task ADD COLUMN priority INTEGER NOT NULL DEFAULT 0
                """.trimIndent()
                )
            }
        }


    }
}
