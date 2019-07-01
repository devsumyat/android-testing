package com.example.androidtesting.add_task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtesting.Event
import com.example.androidtesting.R
import com.example.androidtesting.data.Task
import com.example.androidtesting.data.remote.TaskRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class AddTaskViewModel(private val repo: TaskRepository) : ViewModel() {

    // Two-way databinding, exposing MutableLiveData
    val title = MutableLiveData<String>()

    // Two-way databinding, exposing MutableLiveData
    val description = MutableLiveData<String>()

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarText

    private val _taskUpdated = MutableLiveData<Event<Unit>>()
    val taskUpdatedEvent: LiveData<Event<Unit>> = _taskUpdated

    // Called when clicking on fab.
    fun saveTask(currentTitle:String,currentDescription:String ) {
//        val currentTitle = title.value
//        val currentDescription = description.value

        if (currentTitle == null || currentDescription == null) {
            _snackbarText.value = Event(R.string.empty_task_message)
            return
        }
        if (Task(currentTitle, currentDescription).isEmpty) {
            _snackbarText.value = Event(R.string.empty_task_message)
            return
        }

        createTask(Task(currentTitle, currentDescription))
    }

    private fun createTask(newTask: Task) = viewModelScope.launch {
        repo.saveTask(newTask)
        _taskUpdated.value = Event(Unit)
    }

}