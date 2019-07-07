package com.example.androidtesting.data

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.androidtesting.data.remote.NotesRepository
import com.google.common.collect.Lists
import java.util.LinkedHashMap

class FakeRepository : NotesRepository{

    var tasksServiceData: LinkedHashMap<String, Note> = LinkedHashMap()

    override suspend fun getNotes(): Result<List<Note>> {
        return Result.Success(Lists.newArrayList(tasksServiceData.values))
    }

    override suspend fun saveNote(note: Note) {
        tasksServiceData[note.id] = note
    }
}