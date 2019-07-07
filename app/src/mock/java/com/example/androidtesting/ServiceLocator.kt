package com.example.androidtesting

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.androidtesting.data.local.NotesDatabase
import com.example.androidtesting.data.local.LocalDataSource
import com.example.androidtesting.data.remote.NotesRepositoryImplementation
import com.example.androidtesting.data.remote.NotesRepository

object ServiceLocator {

    private val lock = Any()
    private var database: NotesDatabase? = null
    @Volatile var notesRepository: NotesRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): NotesRepository{
        synchronized(this){
            return notesRepository ?:
            notesRepository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): NotesRepository {
        database = Room.databaseBuilder(context.applicationContext,
            NotesDatabase::class.java, "Notes.db")
            .build()

        return NotesRepositoryImplementation(
            LocalDataSource(database!!.taskDao())
        )
    }

    @VisibleForTesting
    fun resetRepository(){
        synchronized(lock){
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            notesRepository = null
        }
    }
}