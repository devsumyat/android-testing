package com.example.androidtesting.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.androidtesting.data.Note
import com.example.androidtesting.data.remote.NotesRepository
import kotlinx.coroutines.launch
import java.util.ArrayList

class TasksViewModel(private val repo: NotesRepository) : ViewModel() {

    private val _items = MutableLiveData<List<Note>>().apply { value = emptyList() }
    val items: LiveData<List<Note>> = _items

    fun loadTasks() {
        viewModelScope.launch {
            val tasksResult = repo.getNotes()

            val tasksToShow = ArrayList<Note>()
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