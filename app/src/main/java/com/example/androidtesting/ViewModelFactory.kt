package com.example.androidtesting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtesting.add_task.AddTaskViewModel
import com.example.androidtesting.data.remote.TaskRepository
import com.example.androidtesting.tasks.TasksViewModel

class ViewModelFactory constructor(
    private val takeRepository: TaskRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass){
                when{
                    isAssignableFrom(AddTaskViewModel::class.java) ->
                        AddTaskViewModel(takeRepository)
                    isAssignableFrom(TasksViewModel::class.java) ->
                        TasksViewModel(takeRepository)

                    else ->
                        throw IllegalAccessException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T
}