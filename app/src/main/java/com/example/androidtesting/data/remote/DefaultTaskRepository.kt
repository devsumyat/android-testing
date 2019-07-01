package com.example.androidtesting.data.remote

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdlingResource
import com.example.androidtesting.data.Task
import kotlinx.coroutines.*
import java.lang.Exception

class DefaultTaskRepository(
    private val tasksLocalDataSource: TaskDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskRepository {

    override suspend fun getTasks(): Result<List<Task>> {
        EspressoIdlingResource.increment()

        return withContext(ioDispatcher) {
            val newTasks = tasksLocalDataSource.getTasks()
            EspressoIdlingResource.decrement()
            (newTasks as? Result.Success)?.let {
                return@withContext Result.Success(it.data)
            }
            return@withContext Result.Error(Exception("Illegal state"))
        }
    }

    override suspend fun saveTask(task: Task) {
        coroutineScope {
            launch {
                tasksLocalDataSource.saveTask(task)
            }
        }
    }

}