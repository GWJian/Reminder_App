package com.finalproject.reminderapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.finalproject.reminderapp.MyApplication
import com.finalproject.reminderapp.data.model.AlarmItem
import com.finalproject.reminderapp.data.model.Remind
import com.finalproject.reminderapp.databinding.FragmentHomeBinding
import com.finalproject.reminderapp.ui.adapter.RemindAdapter
import com.finalproject.reminderapp.ui.viewModels.HomeViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory
    }

    private lateinit var adapter: RemindAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        lifecycleScope.launch {

            viewModel.getReminder().collect {
                if (it.isEmpty()) {
                    binding.llEmpty.visibility = View.VISIBLE
                } else {
                    binding.llEmpty.visibility = View.GONE
                }
                adapter.setReminders(it)
            }
        }

        binding.efabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddRemindFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }
    }


    //confirm and cancel the reminder at the same time, when the user clicks on the delete button
    fun confirmAndDeleteReminder(remind: Remind, alarmItem: AlarmItem? = null) {
        val builder = AlertDialog.Builder(requireContext())
        val scheduler = (requireActivity().application as MyApplication).scheduler

        builder.setTitle("Confirm Delete")
        builder.setMessage("Are you sure you want to delete this reminder?")

        builder.setPositiveButton("Yes") { dialog, _ ->

            //cancel the alarm when the delete button is clicked
            lifecycleScope.launch {
                alarmItem?.let {
                    scheduler.cancel(it)
                }
            }

            viewModel.deleteReminder(remind)

            Toast.makeText(
                requireContext(),
                "The reminder has been deleted successfully!",
                Toast.LENGTH_LONG
            ).show()
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val deleteConfirmationDialog = builder.create()
        deleteConfirmationDialog.show()
    }


    fun setupAdapter() {
        adapter = RemindAdapter(
            emptyList(),
            {
                confirmAndDeleteReminder(it)
            },
            {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToUpdateRemindFragment(it.id!!)
                NavHostFragment.findNavController(this).navigate(action)
            }

        )

        val layoutManager = LinearLayoutManager(requireContext())

        binding.rvReminders.adapter = adapter
        binding.rvReminders.layoutManager = layoutManager
    }


}

