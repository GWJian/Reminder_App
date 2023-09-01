package com.finalproject.reminderapp.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.finalproject.reminderapp.R
import com.finalproject.reminderapp.databinding.FragmentHomeBinding
import com.finalproject.reminderapp.ui.adapter.TaskAdapter
import com.finalproject.reminderapp.ui.viewModels.HomeViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory
    }

    private lateinit var adapter: TaskAdapter

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
            viewModel.getTasks().collect {
                if (it.isEmpty()) {
                    binding.llEmpty.visibility = View.VISIBLE
                } else {
                    binding.llEmpty.visibility = View.GONE
                }
                adapter.setTasks(it)
            }
        }

        binding.eFabAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddTaskFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }
    }

    fun setupAdapter() {
        adapter = TaskAdapter(
            emptyList(),
            {
                viewModel.deleteTask(it)
            },
            {
                val action = HomeFragmentDirections.actionHomeFragmentToUpdateTaskFragment(it.id!!)
                NavHostFragment.findNavController(this).navigate(action)
            }

        )


        val layoutManager = LinearLayoutManager(requireContext())

        //val StaggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.rvTask.adapter = adapter
        binding.rvTask.layoutManager = layoutManager
    }

}