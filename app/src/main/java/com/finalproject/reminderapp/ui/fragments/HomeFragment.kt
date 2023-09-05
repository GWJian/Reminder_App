package com.finalproject.reminderapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
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
            //if the list is empty show the empty view
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

//    fun setupAdapter() {
//        adapter = RemindAdapter(
//            emptyList(),
//            {
//                viewModel.deleteReminder(it)
//            },
//            {
//                val action = HomeFragmentDirections.actionHomeFragmentToUpdateRemindFragment(it.id!!)
//                NavHostFragment.findNavController(this).navigate(action)
//            }
//
//
//
//        )
//
//        val layoutManager = LinearLayoutManager(requireContext())
//
//        binding.rvReminders.adapter = adapter
//        binding.rvReminders.layoutManager = layoutManager
//    }

    fun setupAdapter() {
        adapter = RemindAdapter(
            emptyList(),
            {
                viewModel.confirmAndDeleteReminder(requireContext(), it)
            },
            {
                val action = HomeFragmentDirections.actionHomeFragmentToUpdateRemindFragment(it.id!!)
                NavHostFragment.findNavController(this).navigate(action)
            }

        )

        val layoutManager = LinearLayoutManager(requireContext())

        binding.rvReminders.adapter = adapter
        binding.rvReminders.layoutManager = layoutManager
    }


}

