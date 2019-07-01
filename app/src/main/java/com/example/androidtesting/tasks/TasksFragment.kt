package com.example.androidtesting.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.androidtesting.EventObserver
import com.example.androidtesting.R
import com.example.androidtesting.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_tasks.*
import timber.log.Timber
import java.util.ArrayList

class TasksFragment : Fragment() {

    private lateinit var listAdapter: TasksAdapter
    private lateinit var viewModel: TasksViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = obtainViewModel(TasksViewModel::class.java)
        viewModel.loadTasks()
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()

        fab_add_task.setOnClickListener {
            findNavController().navigate(R.id.addTask)
        }


        viewModel.items.observe(this, Observer {
            listAdapter.replaceData(it)
        })
    }

    private fun setupListAdapter() {
        listAdapter = TasksAdapter(ArrayList(0), viewModel)
        tasks_list.adapter = listAdapter
    }
}