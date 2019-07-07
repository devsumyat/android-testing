package com.example.androidtesting.data.remote

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdlingResource
import com.example.androidtesting.data.Note
import kotlinx.coroutines.*
import java.lang.Exception

class NotesRepositoryImplementation(
    private val tasksLocalDataSource: NotesDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NotesRepository {

    override suspend fun getNotes(): Result<List<Note>> {
        EspressoIdlingResource.increment()

        return withContext(ioDispatcher) {
            val newTasks = tasksLocalDataSource.getTasks()
            EspressoIdlingResource.decrement()
            (newTasks as? Result.Success)?.let {
                return@withContext Result.Success(it.data)
            }
            return@withContext Result.Error(Exception("Illegal state"))
        }
    }

    override suspend fun saveNote(note: Note) {
        coroutineScope {
            launch {
                tasksLocalDataSource.saveTask(note)
            }
        }
    }

}