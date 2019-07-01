package com.example.androidtesting.data

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.androidtesting.data.remote.TaskRepository
import com.google.common.collect.Lists
import java.util.LinkedHashMap

class FakeRepository : TaskRepository{

    var tasksServiceData: LinkedHashMap<String, Task> = LinkedHashMap()

    override suspend fun getTasks(): Result<List<Task>> {
        return Result.Success(Lists.newArrayList(tasksServiceData.values))
    }

    override suspend fun saveTask(task: Task) {
        tasksServiceData[task.id] = task
    }
}