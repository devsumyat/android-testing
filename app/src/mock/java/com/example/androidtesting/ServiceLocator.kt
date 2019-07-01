package com.example.androidtesting

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.androidtesting.data.local.TaskDatabase
import com.example.androidtesting.data.local.TaskLocalDataSource
import com.example.androidtesting.data.remote.DefaultTaskRepository
import com.example.androidtesting.data.remote.TaskRepository

object ServiceLocator {

    private val lock = Any()
    private var database: TaskDatabase? = null
    @Volatile var tasksRepository: TaskRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): TaskRepository{
        synchronized(this){
            return tasksRepository ?:
            tasksRepository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): TaskRepository {
        database = Room.databaseBuilder(context.applicationContext,
            TaskDatabase::class.java, "Tasks.db")
            .build()

        return DefaultTaskRepository(
            TaskLocalDataSource(database!!.taskDao())
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
            tasksRepository = null
        }
    }
}