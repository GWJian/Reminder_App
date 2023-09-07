package com.finalproject.reminderapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import com.finalproject.reminderapp.MyApplication
import com.finalproject.reminderapp.R
import com.finalproject.reminderapp.data.model.AlarmItem
import com.finalproject.reminderapp.data.repo.AndroidAlarmScheduler
import com.finalproject.reminderapp.databinding.FragmentAddUpdateRemindBinding
import com.finalproject.reminderapp.ui.viewModels.BaseRemindViewModel
import com.google.android.material.snackbar.Snackbar
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
        //call the toggleSwitch function from the viewModel
        val scheduler = (requireActivity().application as MyApplication).scheduler

        binding.toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleSwitch()

            val dateTimes = viewModel.getCustomDateTime()
            val alarmItem = dateTimes?.let { dateTime ->
                val (year, month, day) = dateTime.date
                val (hour, minute) = dateTime.time

                val localDateTime = LocalDateTime.of(year, month, day, hour, minute, 0)
                AlarmItem(localDateTime, binding.tvTitle.text.toString())
            }

            alarmItem?.let {
                if (isChecked) {
                    scheduler.schedule(it)
                } else {
                    scheduler.cancel(it)
                }
            }
        }


        binding.btnSubmit.setOnClickListener {
            //abstract and submit the reminder to the database
            viewModel.submit()

//            //get the date and time from the user and set it to the alamItem
//            val dateTime = viewModel.getCustomDateTime()
//            if (dateTime == null) {
//                //TODO
//            } else {
//                //get the date and time from the user and set it to the alamItem
//                val (year, month, day) = dateTime.date
//                val (hour, minute) = dateTime.time
//
//                val localDateTime = LocalDateTime.of(
//                    year,
//                    month,
//                    day,
//                    hour,
//                    minute,
//                    0
//                )
//                //this alamItem will be used to schedule the alarm in the AndroidAlarmScheduler.kt
//                alarmItem = AlarmItem(localDateTime, binding.tvTitle.text.toString())
//                alarmItem?.let {
//                    val task = scheduler.schedule(it)
//                }
//            }
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