package com.example.androidtesting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtesting.add_note.AddNoteViewModel
import com.example.androidtesting.data.remote.NotesRepository
import com.example.androidtesting.notes.TasksViewModel

class ViewModelFactory constructor(
    private val notesRepository: NotesRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass){
                when{
                    isAssignableFrom(AddNoteViewModel::class.java) ->
                        AddNoteViewModel(notesRepository)
                    isAssignableFrom(TasksViewModel::class.java) ->
                        TasksViewModel(notesRepository)

                    else ->
                        throw IllegalAccessException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T
}