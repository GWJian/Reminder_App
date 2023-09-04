package com.finalproject.reminderapp.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.reminderapp.R
import com.finalproject.reminderapp.ui.viewModels.UpdateRemindViewModel

class UpdateRemindFragment : Fragment() {

    companion object {
        fun newInstance() = UpdateRemindFragment()
    }

    private lateinit var viewModel: UpdateRemindViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_update_remind, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UpdateRemindViewModel::class.java)
        // TODO: Use the ViewModel
    }

}