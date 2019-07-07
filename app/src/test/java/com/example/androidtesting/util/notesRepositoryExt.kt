/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androidtesting.util

import com.example.androidtesting.data.Note
import com.example.androidtesting.data.remote.NotesRepository
import kotlinx.coroutines.runBlocking

/**
 * A blocking version of TasksRepository.saveNote to minimize the number of times we have to
 * explicitly add <code>runBlocking { ... }</code> in our tests
 */
fun NotesRepository.saveTaskBlocking(note: Note) = runBlocking {
    this@saveTaskBlocking.saveNote(note)
}

fun NotesRepository.getTasksBlocking() = runBlocking {
    this@getTasksBlocking.getNotes()
}