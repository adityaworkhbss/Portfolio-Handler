package com.aditya.porfiliohandler.presenter.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aditya.porfiliohandler.R
import com.aditya.porfiliohandler.adapter.DashboardGridAdapter
import com.aditya.porfiliohandler.databinding.FragmentDashboardBinding
import com.aditya.porfiliohandler.presenter.viewmodel.MainViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DashboardGridAdapter
    
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        
        setupRecyclerView()
        observeViewModel()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = DashboardGridAdapter(emptyList()) { item ->
            findNavController().navigate(item.navigationId)
        }

        binding.dashboardRecyclerView.layoutManager =
            GridLayoutManager(
                requireContext(),
                2
            )

        binding.dashboardRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.dashboard.observe(viewLifecycleOwner){ dashboard ->
            val items = listOf(
                DashboardItem("Blogs", dashboard.blogs.size, R.id.blogsFragment),
                DashboardItem("Messages", dashboard.messages.size, R.id.messagesFragment),
                DashboardItem("Experience", dashboard.experience.size, R.id.experienceFragment),
                DashboardItem("Projects", dashboard.projects.size, R.id.projectsFragment)
            )
            adapter.updateItems(items)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}