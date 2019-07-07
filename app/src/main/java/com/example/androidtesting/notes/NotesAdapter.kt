package com.example.androidtesting.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import com.example.androidtesting.data.Note
import com.example.androidtesting.databinding.TaskItemBinding
import timber.log.Timber

class NotesAdapter(
    private var notes: List<Note>,
    private var viewModel: TasksViewModel
) : BaseAdapter() {

    fun replaceData(notes: List<Note>) {
        setList(notes)
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

        with(binding) {
            note = notes[position]
            executePendingBindings()
        }

        return binding.root
    }

    override fun getItem(position: Int) = notes[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = notes.size

    private fun setList(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }
}