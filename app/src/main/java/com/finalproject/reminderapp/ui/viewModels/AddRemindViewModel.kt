package com.finalproject.reminderapp.ui.viewModels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finalproject.reminderapp.MyApplication
import com.finalproject.reminderapp.data.repo.RemindersRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddRemindViewModel(
    private val repo: RemindersRepo
) : BaseRemindViewModel() {

    fun addReminder() {
        val remind = validateAndGetReminder()
        remind?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repo.addRemind(remind)
                finish.emit(Unit)
            }
        }
    }

    override fun submit() {
        //override the submit function from the BaseRemindViewModel, to add the reminder to the database
        addReminder()
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication).remindersRepo
                AddRemindViewModel(
                    repo = myRepository,
                )
            }
        }
    }

}