package com.example.androidtesting.data.remote

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.androidtesting.data.Note

interface NotesDataSource {
    suspend fun getTasks(): Result<List<Note>>

    suspend fun saveTask(note: Note)
}