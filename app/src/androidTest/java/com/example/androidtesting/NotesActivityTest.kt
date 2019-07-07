package com.example.androidtesting

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdlingResource
import com.example.androidtesting.data.Note
import com.example.androidtesting.data.remote.NotesRepository
import com.example.androidtesting.util.DataBindingIdlingResource
import com.example.androidtesting.util.monitorActivity
import com.example.androidtesting.util.saveTaskBlocking2
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NotesActivityTest {

    private lateinit var repository: NotesRepository

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun init() {
        repository = ServiceLocator.provideTasksRepository(ApplicationProvider.getApplicationContext())
    }

    @After
    fun reset() {
        ServiceLocator.resetRepository()
    }

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @Test
    fun saveTask(){
        repository.saveTaskBlocking2(Note("TITLE1", "DESCRIPTION"))

        // start up Tasks screen
        val activityScenario = ActivityScenario.launch(NotesActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Verify task is displayed on screen in the task list.
        Espresso.onView(ViewMatchers.withText("TITLE1")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // Verify previous task is not displayed
        Espresso.onView(ViewMatchers.withText("NEW TITLE")).check(ViewAssertions.doesNotExist())
    }
}