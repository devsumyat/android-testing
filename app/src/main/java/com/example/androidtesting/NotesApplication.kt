package com.example.androidtesting

import android.app.Application
import com.example.androidtesting.data.remote.TaskRepository
import com.facebook.stetho.Stetho
import timber.log.Timber

class NotesApplication : Application(){

    val taskRepository: TaskRepository
    get() = ServiceLocator.provideTasksRepository(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
    }
}