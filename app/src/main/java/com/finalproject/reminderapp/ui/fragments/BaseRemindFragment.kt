package com.finalproject.reminderapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import com.finalproject.reminderapp.R
import com.finalproject.reminderapp.databinding.FragmentAddUpdateRemindBinding
import com.finalproject.reminderapp.ui.viewModels.BaseRemindViewModel
import com.google.android.material.snackbar.Snackbar

abstract class BaseRemindFragment : Fragment() {
    abstract val viewModel: BaseRemindViewModel
    protected lateinit var binding: FragmentAddUpdateRemindBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUpdateRemindBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

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