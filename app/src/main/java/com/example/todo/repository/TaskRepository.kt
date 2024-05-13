package com.example.todo.repository

import androidx.room.Query
import com.example.todo.model.Task
import com.example.todo.model.TaskDatabase


class TaskRepository(private val  db: TaskDatabase) {

    suspend fun insertTask(task: Task) = db.getTaskDao().insertTask(task)
    suspend fun deleteTask(task: Task) = db.getTaskDao().deleteTask(task)
    suspend fun updateTask(task: Task) = db.getTaskDao().updateTask(task)

    fun getAllTasks() = db.getTaskDao().getAllTasks()
    fun searchTask(query: String?) = db.getTaskDao().searchTask(query)
}