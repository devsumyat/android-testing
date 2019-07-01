package com.example.androidtesting.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import com.example.androidtesting.data.Task
import com.example.androidtesting.databinding.TaskItemBinding
import timber.log.Timber

class TasksAdapter(
    private var tasks: List<Task>,
    private var viewModel: TasksViewModel
) : BaseAdapter() {

    fun replaceData(tasks: List<Task>) {
        setList(tasks)
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val binding: TaskItemBinding
        binding = if (view == null) {
            // Inflate
            val inflater = LayoutInflater.from(viewGroup?.context)

            // Create the binding
            TaskItemBinding.inflate(inflater, viewGroup, false)
        } else {
            // Recycling view
            DataBindingUtil.getBinding(view) ?: throw IllegalStateException()
        }

        val userActionsListener = object : TaskItemUserActionsListener {
            override fun onCompleteChanged(task: Task, v: View) {
                val checked = (v as CheckBox).isChecked
                Timber.w("clickon complete")
//                tasksViewModel.completeTask(task, checked)
            }

            override fun onTaskClicked(task: Task) {
                Timber.w("clickon open")
//                tasksViewModel.openTask(task.id)
            }
        }

        with(binding) {
            task = tasks[position]
            listener = userActionsListener
            executePendingBindings()
        }

        return binding.root
    }

    override fun getItem(position: Int) = tasks[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = tasks.size

    private fun setList(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}