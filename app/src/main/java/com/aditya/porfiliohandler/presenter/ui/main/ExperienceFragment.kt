package com.aditya.porfiliohandler.presenter.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.porfiliohandler.adapter.ExperienceAdapter
import com.aditya.porfiliohandler.bottomsheets.ExperienceBottomSheet
import com.aditya.porfiliohandler.databinding.FragmentExperienceBinding
import com.aditya.porfiliohandler.domain.model.Experience
import com.aditya.porfiliohandler.presenter.viewmodel.MainViewModel

class ExperienceFragment : Fragment() {

    private var _binding: FragmentExperienceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: ExperienceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExperienceBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeViewModel()
        setupAddButton()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = ExperienceAdapter(emptyList(),
            onExperienceClick = { experience ->
                bottomSheetExperienceEdit(experience)
            }
        )
        binding.experienceRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.experienceRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.dashboard.observe(viewLifecycleOwner) { dashboard ->
            val experience = dashboard.experience
            if (experience.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.experienceRecyclerView.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.experienceRecyclerView.visibility = View.VISIBLE
                adapter.updateItems(experience)
            }
        }
    }

    private fun setupAddButton() {
        binding.fabAddExperience.setOnClickListener {
            bottomSheetExperienceAdd()
        }
    }

    private fun bottomSheetExperienceEdit(experience: Experience) {
        val sheet = ExperienceBottomSheet(experience) { updatedExperience ->
            viewModel.updateExperience(updatedExperience)
        }
        sheet.show(parentFragmentManager, "ExperienceEditBottomSheet")
    }

    private fun bottomSheetExperienceAdd() {
        val sheet = ExperienceBottomSheet { newExperience ->
            viewModel.addExperience(newExperience)
        }
        sheet.show(parentFragmentManager, "ExperienceAddBottomSheet")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}