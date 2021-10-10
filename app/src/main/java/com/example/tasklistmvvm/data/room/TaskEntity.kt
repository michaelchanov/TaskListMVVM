package com.example.tasklistmvvm.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_info")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val title: String?,
    val priority: Boolean?,
    val text: String?
)
