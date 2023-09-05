package com.finalproject.reminderapp.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.finalproject.reminderapp.R
import com.finalproject.reminderapp.ui.utils.DateTimeUtil
import com.finalproject.reminderapp.ui.viewModels.AddRemindViewModel

class AddRemindFragment: BaseRemindFragment() {
    override val viewModel: AddRemindViewModel by viewModels {
        AddRemindViewModel.Factory
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnSubmit.setOnClickListener {
            viewModel.addReminder()
        }

        binding.btnDate.setOnClickListener {

            val date = DateTimeUtil.getDate()

            DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                binding.tvDate.text = "$dayOfMonth/${month + 1}/$year"
            }, date.year, date.month, date.day).show()
        }

        binding.btnTime.setOnClickListener {

            val time = DateTimeUtil.getTime()

            TimePickerDialog(requireContext(), { _, hour, minute ->
                binding.tvTime.text = "$hour:$minute"
            }, time.hour, time.minute, true).show()
        }

    }

}