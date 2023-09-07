package com.finalproject.reminderapp.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.reminderapp.data.model.Remind
import com.finalproject.reminderapp.ui.model.CustomDate
import com.finalproject.reminderapp.ui.model.CustomDateTime
import com.finalproject.reminderapp.ui.model.CustomTime
import com.finalproject.reminderapp.ui.utils.FieldAndTeg
import com.finalproject.reminderapp.ui.utils.Validation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseRemindViewModel(): ViewModel() {
    val title: MutableStateFlow<String> = MutableStateFlow("")
    val desc: MutableStateFlow<String> = MutableStateFlow("")
    val date: MutableStateFlow<String> = MutableStateFlow("")
    val time: MutableStateFlow<String> = MutableStateFlow("")
    val error: MutableSharedFlow<String> = MutableSharedFlow()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    protected var customDate: CustomDate? = null
    protected var customTime: CustomTime? = null

    protected fun validateAndGetReminder(): Remind? {
        val result = Validation.validation(
            FieldAndTeg(
                name = "Title",
                value = title.value,
                reg = "^(?=.*[A-Za-z0-9]).{2,20}\$"
            ),
            FieldAndTeg(
                name = "Description",
                value = desc.value,
                reg = "^(?=.*[A-Za-z0-9]).{2,}\$"
            ),
            FieldAndTeg(
                name = "Date",
                value = date.value,
                reg = ".{1,}"
            ),
            FieldAndTeg(
                name = "Time",
                value = time.value,
                reg = ".{1,}"
            ),

            )
        if (result.first) {
            return Remind(
                title = title.value,
                desc = desc.value,
                date = date.value,
                time = time.value,
            )
        } else {
            viewModelScope.launch {
                error.emit(result.second)
            }
        }
        return null
    }

    fun setCustomTime(hh: Int, mm: Int) {
        //set the time to the customTime
        customTime = CustomTime(hh, mm)
    }

    fun setCustomDate(yy: Int, mm: Int, dd: Int) {
        //set the date to the customDate
        customDate = CustomDate(yy, mm, dd)
    }

    //get the date and time from the user and set it to the alamItem
    fun getCustomDateTime(): CustomDateTime? {
        //check if the user has selected the date and time, if not return null
        if (customTime == null || customDate == null) {
            return null
        }

        //CustomDateTime is a data class that contains the date and time, it will be used to set the alarm
        return CustomDateTime(
            customDate!!,
            customTime!!
        )
    }

    //abstract function to submit the reminder, this function will be implemented in the AddRemindViewModel.kt and UpdateRemindViewModel.kt
    abstract fun submit()

    //toggleSwitch function will be implemented in the AddRemindViewModel.kt and UpdateRemindViewModel.kt
    abstract fun toggleSwitch()

}