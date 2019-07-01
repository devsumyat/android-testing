package com.example.androidtesting.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.androidtesting.data.Task
import com.example.androidtesting.data.remote.TaskRepository
import kotlinx.coroutines.launch
import java.util.ArrayList

class TasksViewModel(private val repo: TaskRepository) : ViewModel() {

    private val _items = MutableLiveData<List<Task>>().apply { value = emptyList() }
    val items: LiveData<List<Task>> = _items

    fun loadTasks() {
        viewModelScope.launch {
            val tasksResult = repo.getTasks()

            val tasksToShow = ArrayList<Task>()
            if (tasksResult is Result.Success) {
                val tasks = tasksResult.data

                for (task in tasks) {
                    tasksToShow.add(task)
                }
                _items.value = ArrayList(tasksToShow)
            } else {
                _items.value = emptyList()
            }
        }
    }
}