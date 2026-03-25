package com.ndPractice.todoapp.Database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ndPractice.todoapp.Database.entities.Task

@Dao
interface TaskDao {
    @Insert
    fun addtask (todo: Task)

    @Delete
    fun deleteTask (task: Task)

    @Update
    fun updateTask (task: Task)

    @Query("SELECT * FROM Task")
    fun getAllTasks (): List<Task>

    @Query("SELECT * FROM Task WHERE date = :filterdate")
    fun getTasksByDate (filterdate: Long): List<Task>



}