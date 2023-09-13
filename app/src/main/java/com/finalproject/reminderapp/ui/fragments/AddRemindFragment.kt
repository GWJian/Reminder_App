package com.finalproject.reminderapp.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData

import com.finalproject.reminderapp.ui.utils.DateTimeUtil
import com.finalproject.reminderapp.ui.viewModels.AddRemindViewModel

class AddRemindFragment : BaseRemindFragment() {
    override val viewModel: AddRemindViewModel by viewModels {
        AddRemindViewModel.Factory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDate.setOnClickListener {
            val date = DateTimeUtil.getDate()

            val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                binding.tvDate.text = "$dayOfMonth/${month + 1}/$year"
                viewModel.setCustomDate(year, month + 1, dayOfMonth)
            }, date.year, date.month, date.day)

            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        binding.btnTime.setOnClickListener {
            //call the function getTime() from DateTimeUtil.kt
            val time = DateTimeUtil.getTime()

            TimePickerDialog(requireContext(), { _, hour, minute ->
                binding.tvTime.text = "$hour:$minute"
                viewModel.setCustomTime(hour, minute)
            }, time.hour, time.minute, true).show()
        }

    }

}