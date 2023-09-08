package com.finalproject.reminderapp.ui.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.finalproject.reminderapp.MyApplication
import com.finalproject.reminderapp.data.repo.RemindersRepo
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

    override fun toggleSwitch() {
        //TODO
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repo.getRemindById(id)
            res?.let {
                title.value = it.title
                desc.value = it.desc
                date.value = it.date
                time.value = it.time

                //split the date by / to get the day, month and year
                val dateTemp = it.date.split("/")
                //dateTemp.size == 3 means that the date is in the format dd/mm/yyyy
                if (dateTemp.size == 3) {
                    setCustomDate(
                        dd = dateTemp[0].toInt(),
                        mm = dateTemp[1].toInt(),
                        yy = dateTemp[2].toInt()
                    )
                }

                //split the time by : to get the hour and minute
                val dateTime = it.date.split(":")
                //dateTime.size == 2 means that the time is in the format hh:mm
                if (dateTime.size == 2) {
                    setCustomTime(hh = dateTime[0].toInt(), mm = dateTime[1].toInt())
                }

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