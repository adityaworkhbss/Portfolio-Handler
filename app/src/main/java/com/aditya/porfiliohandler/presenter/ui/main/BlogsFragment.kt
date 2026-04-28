package com.aditya.porfiliohandler.presenter.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.porfiliohandler.adapter.BlogsAdapter
import com.aditya.porfiliohandler.databinding.FragmentBlogsBinding
import com.aditya.porfiliohandler.domain.model.Blogs
import com.aditya.porfiliohandler.presenter.responseHandler.UIEvent
import com.aditya.porfiliohandler.presenter.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
        adapter = BlogsAdapter(emptyList(),
            onPublishClick = { blogs ->
                showPublishAlert(blogs)
            },
            onDeleteClick = { blogs ->
                showDeleteAlert(blogs)
            }
        )
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

        viewModel.uiEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is UIEvent.ShowToast -> {
                    showProgress(false)
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is UIEvent.ShowError -> {
                    showProgress(false)
                    showErrorDialog(event.message)
                }
                is UIEvent.loading -> {
                    showProgress(true)
                }
                is UIEvent.success -> {
                    showProgress(false)
                }
            }
        }
    }

    private fun showDeleteAlert(blogs: Blogs){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Blog")
            .setMessage("Are you sure you want to delete this blog?")
            .setCancelable(false)
            .setPositiveButton("Delete") { dialog, _ ->
                viewModel.deleteBlog(blogs)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showPublishAlert(blogs: Blogs){

        var alertTitle = ""
        var alertMessage = ""
        var alertButton = ""

        val isPublished = blogs.published
        if(isPublished){
            alertTitle = "Unpublish Blog"
            alertMessage = "Are you sure you want to unpublish this blog?"
            alertButton = "Unpublish"
        } else {
            alertTitle = "Publish Blog"
            alertMessage = "Are you sure you want to publish this blog?"
            alertButton = "Publish"
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(alertTitle)
            .setMessage(alertMessage)
            .setCancelable(false)
            .setPositiveButton(alertButton) { dialog, _ ->
                viewModel.publishBlog(blogs, isPublished)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun showProgress(toShow: Boolean) {
        binding.progressBar.isVisible = toShow
    }

    fun showErrorDialog(message : String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}