package com.example.androidtesting.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.androidtesting.R
import com.example.androidtesting.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_notes.*
import java.util.ArrayList

class NotesFragment : Fragment() {

    private lateinit var listAdapter: NotesAdapter
    private lateinit var viewModel: TasksViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = obtainViewModel(TasksViewModel::class.java)
        viewModel.loadTasks()
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()

        fab_add_task.setOnClickListener {
            findNavController().navigate(R.id.addTask)
        }


        viewModel.items.observe(this, Observer {
            no_notes.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
                listAdapter.replaceData(it)
        })
    }

    private fun setupListAdapter() {
        listAdapter = NotesAdapter(ArrayList(0), viewModel)
        tasks_list.adapter = listAdapter
    }
}