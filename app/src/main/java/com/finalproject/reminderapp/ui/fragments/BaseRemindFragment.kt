package com.finalproject.reminderapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.finalproject.reminderapp.MyApplication
import com.finalproject.reminderapp.R
import com.finalproject.reminderapp.data.model.AlarmItem
import com.finalproject.reminderapp.databinding.FragmentAddUpdateRemindBinding
import com.finalproject.reminderapp.ui.viewModels.BaseRemindViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.time.LocalDateTime

abstract class BaseRemindFragment : Fragment() {
    abstract val viewModel: BaseRemindViewModel
    var alarmItem: AlarmItem? = null

    protected lateinit var binding: FragmentAddUpdateRemindBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUpdateRemindBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        //initialize the scheduler and the repo in the application class so that it can be accessed from anywhere in the app
        val scheduler = (requireActivity().application as MyApplication).scheduler

        //set the date and time to the textViews
        viewModel.date.asLiveData().observe(viewLifecycleOwner) {
            binding.tvDate.text = it
        }

        //get time from the viewModel and cancel the alarm
        binding.btnStop.setOnClickListener {
            //TODO: need to get the time and date from the database to cancel the alarm
        }


        binding.btnSubmit.setOnClickListener {
            //TODO: need to get time,date and title from database to click update again
            //abstract and submit the reminder to the database
            viewModel.submit()

            val dateTimes = viewModel.getCustomDateTime()

            alarmItem = dateTimes?.let { dateTime ->
                val (year, month, day) = dateTime.date
                val (hour, minute) = dateTime.time

                val localDateTime = LocalDateTime.of(year, month, day, hour, minute, 0)
                AlarmItem(localDateTime, binding.tvTitle.text.toString())
            }

            alarmItem?.let {
                scheduler.schedule(it)
            }
        }


        viewModel.finish.asLiveData().observe(viewLifecycleOwner) {
            NavHostFragment.findNavController(this).popBackStack()
        }

        viewModel.error.asLiveData().observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_LONG
            )
            snackbar.also {
                it.setBackgroundTint(
                    ContextCompat.getColor(requireContext(), R.color.red)
                )
            }.show()
        }
    }

}