package com.example.androidtesting.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.androidtesting.TodoApplication
import com.example.androidtesting.ViewModelFactory


fun <T: ViewModel> Fragment.obtainViewModel(viewModelClass : Class<T>): T{
    val repo = (requireContext().applicationContext as TodoApplication).taskRepository
    return ViewModelProviders.of(this, ViewModelFactory(repo)).get(viewModelClass)
}