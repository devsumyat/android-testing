package com.example.androidtesting.add_task_test

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.androidtesting.R
import com.example.androidtesting.ServiceLocator
import com.example.androidtesting.add_task.AddTaskFragment
import com.example.androidtesting.data.FakeRepository
import com.example.androidtesting.data.remote.TaskRepository
import com.example.androidtesting.util.getTasksBlocking
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.TextLayoutMode


@ObsoleteCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
@LooperMode(LooperMode.Mode.PAUSED)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
class AddTaskFragmentShareTest{

    private lateinit var repository: TaskRepository

    @Before
    fun initRepository(){
        repository = FakeRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanupDb() = runBlocking {
        ServiceLocator.resetRepository()
    }

    @Test
    fun validTask_isSaved() {
        // GIVEN - On the "Add Task" screen.
        val navController = Mockito.mock(NavController::class.java)
        launchFragment(navController)

        // WHEN - Valid title and description combination and click save
        Espresso.onView(ViewMatchers.withId(R.id.add_task_title)).perform(ViewActions.replaceText("title"))
        Espresso.onView(ViewMatchers.withId(R.id.add_task_description)).perform(ViewActions.replaceText("description"))
        Espresso.onView(ViewMatchers.withId(R.id.fab_save_task)).perform(ViewActions.click())

        // THEN - Verify that the repository saved the task
        val tasks = (repository.getTasksBlocking() as Result.Success).data
        Assert.assertEquals(tasks.size, 1)
        Assert.assertEquals(tasks[0].title, "title")
        Assert.assertEquals(tasks[0].description, "description")
    }

    private fun launchFragment(navController: NavController?) {
        val scenario = launchFragmentInContainer<AddTaskFragment>(null, R.style.AppTheme)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }
}