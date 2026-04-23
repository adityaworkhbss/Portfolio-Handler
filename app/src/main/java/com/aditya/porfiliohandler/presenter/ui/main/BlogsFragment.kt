package com.aditya.porfiliohandler.presenter.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.porfiliohandler.adapter.BlogsAdapter
import com.aditya.porfiliohandler.databinding.FragmentBlogsBinding
import com.aditya.porfiliohandler.presenter.viewmodel.MainViewModel

class BlogsFragment : Fragment() {

    private var _binding: FragmentBlogsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: BlogsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlogsBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeViewModel()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = BlogsAdapter(emptyList())
        binding.blogsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.blogsRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.dashboard.observe(viewLifecycleOwner) { dashboard ->
            val blogs = dashboard.blogs
            if (blogs.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.blogsRecyclerView.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.blogsRecyclerView.visibility = View.VISIBLE
                adapter.updateItems(blogs)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}