package com.example.androidtesting.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidtesting.data.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(note: Note)

    @Query("SELECT * FROM NOTES")
    suspend fun getTasks(): List<Note>
}