package com.example.androidtesting.data.local

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.androidtesting.data.Task
import com.example.androidtesting.data.remote.TaskDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskLocalDataSource internal constructor(
    private val tasksDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : TaskDataSource{

    override suspend fun getTasks(): Result<List<Task>> = withContext(ioDispatcher){
        return@withContext try {
            Result.Success(tasksDao.getTasks())
        } catch(e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveTask(task: Task) {
        tasksDao.insertTask(task)
    }

}