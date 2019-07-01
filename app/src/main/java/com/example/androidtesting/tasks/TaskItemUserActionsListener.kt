package com.example.androidtesting.tasks

import android.view.View
import com.example.androidtesting.data.Task

interface TaskItemUserActionsListener {

    fun onCompleteChanged(task: Task, v: View)

    fun onTaskClicked(task: Task)

}