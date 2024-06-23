package com.example.codsoft_task_1.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM task ORDER BY priority ASC")
    fun getTasksOrderedByPriority(): Flow<List<Task>>

    @Query("SELECT * FROM task ORDER BY taskName ASC")
    fun getTasksOrderedByTaskName(): Flow<List<Task>>
}