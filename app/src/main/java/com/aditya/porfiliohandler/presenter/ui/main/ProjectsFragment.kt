package com.aditya.porfiliohandler.presenter.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.porfiliohandler.adapter.ProjectsAdapter
import com.aditya.porfiliohandler.databinding.FragmentProjectsBinding
import com.aditya.porfiliohandler.domain.model.Projects
import com.aditya.porfiliohandler.presenter.viewmodel.MainViewModel

class ProjectsFragment : Fragment() {

    private var _binding: FragmentProjectsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: ProjectsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectsBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeViewModel()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = ProjectsAdapter(emptyList(),
                onClickProjects = { projects ->
                    bottomSheetProjectEdit(projects)
                }

            )
        binding.projectsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.projectsRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.dashboard.observe(viewLifecycleOwner) { dashboard ->
            val projects = dashboard.projects
            if (projects.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.projectsRecyclerView.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.projectsRecyclerView.visibility = View.VISIBLE
                adapter.updateItems(projects)
            }
        }
    }

    private fun bottomSheetProjectEdit(projects: Projects){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}