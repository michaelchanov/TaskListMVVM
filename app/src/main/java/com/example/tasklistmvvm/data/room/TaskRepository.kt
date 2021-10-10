package com.example.tasklistmvvm.data.room

import androidx.lifecycle.LiveData

class TaskRepository(
    private val taskDao: TaskDao
) {


    suspend fun getTask() = taskDao.getTask()

    suspend fun insertTask(task: TaskEntity) = taskDao.insertTask(task)

    suspend fun updateTask(task: TaskEntity) = taskDao.updateTask(task)

    suspend fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)


//    fun getAllTasks(): LiveData<List<TaskEntity>> = taskDao.getAllTasks()

}