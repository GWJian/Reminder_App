package com.finalproject.reminderapp.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.finalproject.reminderapp.R
import com.finalproject.reminderapp.ui.utils.DateTimeUtil
import com.finalproject.reminderapp.ui.viewModels.UpdateRemindViewModel

class UpdateRemindFragment : BaseRemindFragment() {

    override val viewModel: UpdateRemindViewModel by viewModels {
        UpdateRemindViewModel.Factory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnSubmit.text = getString(R.string.update)
        binding.tvTitleName.text = getString(R.string.update_task)

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
            val time = DateTimeUtil.getTime()

            TimePickerDialog(requireContext(), { _, hour, minute ->
                binding.tvTime.text = "$hour:$minute"
                viewModel.setCustomTime(hour, minute)
            }, time.hour, time.minute, true).show()
        }

    }

}