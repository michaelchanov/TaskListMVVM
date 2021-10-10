package com.example.tasklistmvvm.data.room

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository, private val context: Context
) : ViewModel() {
    var allTasks: MutableLiveData<List<TaskEntity>> = MutableLiveData()

    init {
        getAllTasks()
    }

    fun getAllTasksObservers(): MutableLiveData<List<TaskEntity>> {
        return allTasks
        Log.e("Smt", "List = ${allTasks.value}")
    }

    fun getAllTasks(){
        viewModelScope.launch {
            val taskDao = TaskDatabase.getAppDatabase(context)?.getTaskDao()
            val list = taskDao?.getTask()

            allTasks.postValue(list!!)
        }
    }

    suspend fun insertTaskInfo(entity: TaskEntity) {
        viewModelScope.launch {
            val taskDao = TaskDatabase.getAppDatabase(context)?.getTaskDao()
            taskDao?.insertTask(entity)
            getAllTasks()
        }
    }


    suspend fun updateTaskInfo(entity: TaskEntity){
        viewModelScope.launch {
            val taskDao = TaskDatabase.getAppDatabase(context)?.getTaskDao()
            taskDao?.updateTask(entity)
            getAllTasks()
        }
    }
    suspend fun deleteTask(entity: TaskEntity) {
        viewModelScope.launch {
            val taskDao = TaskDatabase.getAppDatabase(context)?.getTaskDao()
            taskDao?.deleteTask(entity)
            getAllTasks()
        }
    }



//    suspend fun getAllTasks() = viewModelScope.launch { repository.getAllTasks() }

}