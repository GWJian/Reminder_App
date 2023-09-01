package com.finalproject.reminderapp.ui.viewModels

import androidx.lifecycle.SavedStateHandle

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finalproject.reminderapp.MyApplication
import com.finalproject.reminderapp.data.repo.TasksRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdatetaskViewModel(

    private val repo: TasksRepo, private val savedStateHandle: SavedStateHandle
) : BaseTaskViewModel() {
    val id = savedStateHandle.get<Int>("taskId") ?: -1


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val myRepository =
                    (this[APPLICATION_KEY] as MyApplication).tasksRepo
                UpdatetaskViewModel(
                    repo = myRepository, savedStateHandle = savedStateHandle
                )
            }
        }
    }

}