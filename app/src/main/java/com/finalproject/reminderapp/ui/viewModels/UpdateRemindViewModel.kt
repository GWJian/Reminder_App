package com.finalproject.reminderapp.ui.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finalproject.reminderapp.MyApplication
import com.finalproject.reminderapp.data.repo.RemindersRepo
import com.finalproject.reminderapp.ui.utils.DateTimeUtil.getDate
import com.finalproject.reminderapp.ui.utils.DateTimeUtil.getTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateRemindViewModel(
    private val repo: RemindersRepo, private val savedStateHandle: SavedStateHandle
) : BaseRemindViewModel() {
    val id = savedStateHandle.get<Int>("reminderId") ?: -1

    fun update() {
        val task = validateAndGetReminder()
        task?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repo.addRemind(it.copy(id))
                finish.emit(Unit)
            }
        }
    }

    override fun submit() {
        //override the submit function from the BaseRemindViewModel
        update()
    }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repo.getRemindById(id)

            res?.let {
                title.value = it.title
                desc.value = it.desc
                date.value = it.date
                time.value = it.time
                isActive.value = it.isActive

                customDate = it.date.getDate()
                customTime = it.date.getTime()
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val myRepository =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication).remindersRepo
                UpdateRemindViewModel(
                    repo = myRepository, savedStateHandle = savedStateHandle
                )
            }
        }
    }


}