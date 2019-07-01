package com.example.androidtesting.data.remote

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.androidtesting.data.Task

interface TaskRepository {

    suspend fun getTasks(): Result<List<Task>>

    suspend fun saveTask(task: Task)

}