package com.finalproject.reminderapp.ui.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finalproject.reminderapp.MyApplication
import com.finalproject.reminderapp.data.model.Remind
import com.finalproject.reminderapp.data.repo.RemindersRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: RemindersRepo
) : ViewModel() {
    fun getReminder(): Flow<List<Remind>> {
        return repo.getRemind()
    }


    fun deleteReminder(remind: Remind) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteRemind(remind)
        }
    }



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication).remindersRepo
                HomeViewModel(
                    repo = myRepository
                )
            }
        }
    }

}
