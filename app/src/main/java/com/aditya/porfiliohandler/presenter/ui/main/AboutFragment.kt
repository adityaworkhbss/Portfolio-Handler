package com.aditya.porfiliohandler.presenter.ui.main

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
import com.aditya.porfiliohandler.databinding.FragmentAboutBinding
import com.aditya.porfiliohandler.domain.model.About
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
        binding.nameText.text = About.name
        binding.roleText.text = About.role
        binding.taglineText.text = About.tagline
        binding.bioText.text = About.bio
        binding.emailText.text = About.email
        binding.locationText.text = About.location
        binding.availabilityText.text = About.availabilityText
    }

    fun setStats(){
        val stats = About.stats
        val statsList = stats

        binding.statsRecyclerView.layoutManager =
            GridLayoutManager(
                requireContext(),
                2
            )

        val adapter = AboutStatsGridAdapter(statsList)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}