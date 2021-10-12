package com.example.tasklistmvvm.data.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TaskDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM task_info ORDER BY id DESC")
    fun getTask(): List<TaskEntity>?




}