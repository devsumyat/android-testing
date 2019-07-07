package com.example.androidtesting.data.remote

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.androidtesting.data.Note

interface NotesRepository {

    suspend fun getNotes(): Result<List<Note>>

    suspend fun saveNote(note: Note)

}