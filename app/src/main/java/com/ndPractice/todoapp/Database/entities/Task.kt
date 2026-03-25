package com.ndPractice.todoapp.Database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey
        (autoGenerate = true) val id: Int = 0,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val description: String,
    @ColumnInfo
    val isCompleted: Boolean = false,
    @ColumnInfo
    val date: Long = System.currentTimeMillis()
)
