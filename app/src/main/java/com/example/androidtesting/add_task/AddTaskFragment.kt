package com.example.androidtesting.add_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.androidtesting.EventObserver
import com.example.androidtesting.R
import com.example.androidtesting.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_add_task.*

class AddTaskFragment : Fragment(){

    private lateinit var viewModel: AddTaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = obtainViewModel(AddTaskViewModel::class.java)
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.taskUpdatedEvent.observe(this, EventObserver{
            findNavController().navigate(R.id.tasks)
        })

        fab_save_task.setOnClickListener {
            viewModel.saveTask(add_task_title.text.toString(), add_task_description.text.toString())
        }
    }
}