package com.finalproject.reminderapp.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.finalproject.reminderapp.R
import com.finalproject.reminderapp.ui.viewModels.AddTaskViewModel

class AddTaskFragment : BaseTaskFragment() {

    override val viewModel: AddTaskViewModel by viewModels {
        AddTaskViewModel.Factory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener {
            viewModel.addTask()
        }
    }


}