package com.finalproject.reminderapp.ui.viewModels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finalproject.reminderapp.MyApplication
import com.finalproject.reminderapp.data.repo.TasksRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskViewModel(
    private val repo: TasksRepo
) : BaseTaskViewModel() {



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository =
                    (this[APPLICATION_KEY] as MyApplication).tasksRepo
                AddTaskViewModel(
                    repo = myRepository,
                )
            }
        }
    }
}

// khayruleslam/