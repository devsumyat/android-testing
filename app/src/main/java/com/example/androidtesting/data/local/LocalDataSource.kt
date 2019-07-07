package com.example.androidtesting.data.local

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.androidtesting.data.Note
import com.example.androidtesting.data.remote.NotesDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource internal constructor(
    private val tasksDao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : NotesDataSource{

    override suspend fun getTasks(): Result<List<Note>> = withContext(ioDispatcher){
        return@withContext try {
            Result.Success(tasksDao.getTasks())
        } catch(e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveTask(note: Note) {
        tasksDao.insertTask(note)
    }

}