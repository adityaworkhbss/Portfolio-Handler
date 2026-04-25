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
import com.aditya.porfiliohandler.adapter.MessagesAdapter
import com.aditya.porfiliohandler.databinding.FragmentMessagesBinding
import com.aditya.porfiliohandler.presenter.ResponseHandler.UIEvent
import com.aditya.porfiliohandler.presenter.viewmodel.MainViewModel

class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: MessagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeViewModel()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = MessagesAdapter(emptyList(),
            onItemDeleteClick = { message ->
                viewModel.deleteMessage(message)
            },

            onItemReadClick = { message ->
                viewModel.readMessage(message)
            }

            )
        binding.messagesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.messagesRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.dashboard.observe(viewLifecycleOwner) { dashboard ->
            val messages = dashboard.messages
            if (messages.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.messagesRecyclerView.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.messagesRecyclerView.visibility = View.VISIBLE
                adapter.updateItems(messages)
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