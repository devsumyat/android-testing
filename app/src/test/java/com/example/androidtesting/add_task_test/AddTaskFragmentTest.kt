package com.example.androidtesting.add_task_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.ViewModelScopeMainDispatcherRule
import com.example.androidtesting.add_task.AddTaskViewModel
import com.example.androidtesting.data.FakeRepository
import com.example.androidtesting.data.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineContext
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ObsoleteCoroutinesApi
class AddTaskFragmentTest {

    private lateinit var viewmodel: AddTaskViewModel

    private lateinit var repo: FakeRepository

    private val testContext = TestCoroutineContext()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesMainDispatcherRule = ViewModelScopeMainDispatcherRule(testContext)

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val task = Task("Title1", "Description1")

    @Before
    fun setupViewModel() {
        // We initialise the repository with no tasks
        repo = FakeRepository()

        // Create class under test
        viewmodel = AddTaskViewModel(repo)
    }

    @ObsoleteCoroutinesApi
    @Test
    fun saveTask(){
        val newTitle = "New Task Title"
        val newDescription = "Some Task Description"
        viewmodel.saveTask(newTitle, newDescription)

        // Execute pending coroutines actions
        testContext.triggerActions()

        val newTask = repo.tasksServiceData.values.first()
        // Then a task is saved in the repository and the view updated
        assertThat(newTask.title).isEqualTo(newTitle)
        assertThat(newTask.description).isEqualTo(newDescription)
    }
}