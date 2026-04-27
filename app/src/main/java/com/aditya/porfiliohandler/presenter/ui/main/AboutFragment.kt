package com.aditya.porfiliohandler.presenter.ui.main

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.porfiliohandler.adapter.AboutSkillsAdapter
import com.aditya.porfiliohandler.adapter.AboutSocialAdapter
import com.aditya.porfiliohandler.adapter.AboutStatsGridAdapter
import com.aditya.porfiliohandler.databinding.DailogUpdateStatsBinding
import com.aditya.porfiliohandler.databinding.FragmentAboutBinding
import com.aditya.porfiliohandler.domain.model.About
import com.aditya.porfiliohandler.domain.model.Stat
import com.aditya.porfiliohandler.presenter.viewmodel.MainViewModel
import com.bumptech.glide.Glide

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var About: About

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        
        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.dashboard.observe(viewLifecycleOwner) { dashboard ->
            dashboard.about?.let { about ->

                About = about

                setBasicDetails()
                setProfileImage()
                setStats()
                setSocialLinks()
                setSkills()
            }
        }
    }

    fun setBasicDetails(){
        binding.nameEdit.setText(About.name)
        binding.roleEdit.setText(About.role)
        binding.taglineEdit.setText(About.tagline)
        binding.bioEdit.setText(About.bio)
        binding.emailEdit.setText(About.email)
        binding.locationEdit.setText(About.location)
        binding.availabilityTextEdit.setText(About.availabilityText)
    }

    fun setStats(){
        val stats = About.stats
        val statsList = stats

        binding.statsRecyclerView.layoutManager =
            GridLayoutManager(
                requireContext(),
                2
            )

        val adapter = AboutStatsGridAdapter(statsList,
            onStatsClick = { stat ->
                showStatsUpdateDialog(stat)
            }
        )
        binding.statsRecyclerView.adapter = adapter
    }

    fun setProfileImage(){
        val imageUrl = About.avatarUrl

        if (imageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .into(binding.avatarImage)
        }
    }

    fun setSocialLinks(){
        val socialList = About.socialLinks

        binding.socialRecyclerView.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

        val socialAdapter = AboutSocialAdapter(socialList)

        binding.socialRecyclerView.adapter = socialAdapter

    }

    fun setSkills(){
        val skills = About.skills

        binding.skillsRecyclerView.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

        val skillsAdapter = AboutSkillsAdapter(skills)
        binding.skillsRecyclerView.adapter = skillsAdapter
    }

    fun showStatsUpdateDialog(stat: Stat) {

        val binding = DailogUpdateStatsBinding.inflate(layoutInflater)

        binding.etLabel.setText(stat.label)
        binding.etValue.setText(stat.value)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Update Stat")
            .setView(binding.root)
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("Update", null)
            .create()

        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {

            val updatedValue = binding.etValue.text.toString().trim()
            val updatedLabel = binding.etLabel.text.toString().trim()

            if (updatedValue.isEmpty() || updatedLabel.isEmpty()) {
                if (updatedValue.isEmpty()) binding.etValue.error = "Required"
                if (updatedLabel.isEmpty()) binding.etLabel.error = "Required"
                return@setOnClickListener
            }

            val updatedStat = stat.copy(
                value = updatedValue,
                label = updatedLabel
            )

            viewModel.updateStat(updatedStat)

            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}