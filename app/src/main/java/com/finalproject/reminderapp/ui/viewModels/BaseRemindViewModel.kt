package com.finalproject.reminderapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.reminderapp.data.model.Remind
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
}