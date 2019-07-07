package com.example.androidtesting.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidtesting.data.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase(){
    abstract fun taskDao(): NoteDao
}