package com.example.codsoft_task_1.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val priority: Int = 0,
    val dueDate: String,
    val isActive: Boolean,
    val taskName: String,
    val taskDescription: String?
)
