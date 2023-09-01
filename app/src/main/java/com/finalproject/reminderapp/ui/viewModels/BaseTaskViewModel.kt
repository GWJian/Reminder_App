package com.finalproject.reminderapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.reminderapp.data.model.Task
import com.finalproject.reminderapp.ui.utils.FieldAndTeg
import com.finalproject.reminderapp.ui.utils.Validation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseTaskViewModel : ViewModel() {
    //StateFlow 是让我们可以在不同的线程中更新数据，而不用担心数据的一致性
    val title: MutableStateFlow<String> = MutableStateFlow("")
    val desc: MutableStateFlow<String> = MutableStateFlow("")
//    val priority:MutableStateFlow<String> = MutableStateFlow("")
    //SharedFlow 可以在不同的线程中更新数据，但是数据的一致性需要我们自己来保证
    val error: MutableSharedFlow<String> = MutableSharedFlow()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()




}