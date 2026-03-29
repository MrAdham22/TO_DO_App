package com.ndPractice.todoapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ndPractice.todoapp.Database.daos.TaskDao
import com.ndPractice.todoapp.Database.entities.Task


@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    companion object {
        private var myDatabase: MyDatabase? = null
     fun creatDatabase(context: Context) {
         myDatabase = Room.databaseBuilder(context, MyDatabase::class.java, "my-database")
             .allowMainThreadQueries()
             .build()
     }
        fun getDatabase(): MyDatabase {
            return myDatabase!!
        }
    }



    abstract fun taskDao(): TaskDao
}
