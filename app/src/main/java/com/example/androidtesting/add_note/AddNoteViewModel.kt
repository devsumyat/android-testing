package com.example.androidtesting.add_note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtesting.Event
import com.example.androidtesting.R
import com.example.androidtesting.data.Note
import com.example.androidtesting.data.remote.NotesRepository
import kotlinx.coroutines.launch

class AddNoteViewModel(private val repo: NotesRepository) : ViewModel() {

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
        if (Note(currentTitle, currentDescription).isEmpty) {
            _snackbarText.value = Event(R.string.empty_task_message)
            return
        }

        createTask(Note(currentTitle, currentDescription))
    }

    private fun createTask(newNote: Note) = viewModelScope.launch {
        repo.saveNote(newNote)
        _taskUpdated.value = Event(Unit)
    }

}