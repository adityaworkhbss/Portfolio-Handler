package com.aditya.porfiliohandler.presenter.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.porfiliohandler.adapter.AboutSkillsAdapter
import com.aditya.porfiliohandler.adapter.AboutSocialAdapter
import com.aditya.porfiliohandler.adapter.AboutStatsGridAdapter
import com.aditya.porfiliohandler.bottomsheets.AboutStatBottomSheet
import com.aditya.porfiliohandler.bottomsheets.SkillCategoryBottomSheet
import com.aditya.porfiliohandler.bottomsheets.SocialLinkBottomSheet
import com.aditya.porfiliohandler.databinding.FragmentAboutBinding
import com.aditya.porfiliohandler.domain.model.About
import com.aditya.porfiliohandler.domain.model.SkillCategory
import com.aditya.porfiliohandler.domain.model.SocialLink
import com.aditya.porfiliohandler.domain.model.Stat
import com.aditya.porfiliohandler.presenter.responseHandler.UIEvent
import com.aditya.porfiliohandler.presenter.viewmodel.MainViewModel
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var About: About

    private val pickImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri ?: return@registerForActivityResult
        viewModel.uploadAvatar(uri)
    }

    private val pickPdf = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri ?: return@registerForActivityResult
        viewModel.uploadResume(uri)
    }

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
                setupAddButtons()
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
            },
            onStatsDelete = { stat ->
                showAlertDeleteStat(stat)
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

        val socialAdapter = AboutSocialAdapter(socialList,
            onSocialClick = { social ->
                showSocialUpdateDialog(social)
            },
            onSocialDelete = { social ->
                showAlertDeleteSocialLink(social)
            }
        )

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

        val skillsAdapter = AboutSkillsAdapter(skills,
            onSkillCategoryClick = { skillCategory ->
                showSkillCategoryUpdateDialog(skillCategory)
            },
            onSkillCategoryDelete = { skillCategory ->
                showAlertDeleteSkillCategory(skillCategory)
            }
        )
        binding.skillsRecyclerView.adapter = skillsAdapter
    }

    private fun setupAddButtons() {
        binding.btnAddSocial.setOnClickListener {
            showSocialAddDialog()
        }

        binding.btnAddSkill.setOnClickListener {
            showSkillCategoryAddDialog()
        }

        // Tap the avatar circle → pick an image
        binding.avatarImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        // "Update Resume" button → pick a PDF
        binding.resumeButton.setOnClickListener {
            pickPdf.launch("application/pdf")
        }
    }

    fun showStatsUpdateDialog(stat: Stat) {
        val sheet = AboutStatBottomSheet(stat) { updatedStat ->
            viewModel.updateStat(updatedStat)
        }
        sheet.show(parentFragmentManager, "AboutStatBottomSheet")
    }

    fun showSocialUpdateDialog(social: SocialLink) {
        val sheet = SocialLinkBottomSheet(social) { updatedSocial ->
            viewModel.updateSocialLink(social, updatedSocial)
        }
        sheet.show(parentFragmentManager, "SocialLinkBottomSheet")
    }

    fun showSocialAddDialog() {
        val sheet = SocialLinkBottomSheet { newSocial ->
            viewModel.addSocialLink(newSocial)
        }
        sheet.show(parentFragmentManager, "SocialLinkAddBottomSheet")
    }

    fun showSkillCategoryUpdateDialog(skillCategory: SkillCategory) {
        val sheet = SkillCategoryBottomSheet(skillCategory) { updatedSkill ->
            viewModel.updateSkillCategory(skillCategory, updatedSkill)
        }
        sheet.show(parentFragmentManager, "SkillCategoryBottomSheet")
    }

    fun showSkillCategoryAddDialog() {
        val sheet = SkillCategoryBottomSheet { newSkill ->
            viewModel.addSkillCategory(newSkill)
        }
        sheet.show(parentFragmentManager, "SkillCategoryAddBottomSheet")
    }

    fun showProgress(toShow: Boolean) {
        binding.progressBar.isVisible = toShow
    }

    fun showErrorDialog(message : String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showAlertDeleteStat(stat: Stat) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Stat")
            .setMessage("Are you sure you want to delete this stat?")
            .setCancelable(false)
            .setPositiveButton("Delete") { dialog, _ ->
                viewModel.deleteStat(stat)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showAlertDeleteSocialLink(socialLink: SocialLink) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Social Link")
            .setMessage("Are you sure you want to delete this social link?")
            .setCancelable(false)
            .setPositiveButton("Delete") { dialog, _ ->
                viewModel.deleteSocialLink(socialLink)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showAlertDeleteSkillCategory(skillCategory: SkillCategory) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Skill Category")
            .setMessage("Are you sure you want to delete this skill category?")
            .setCancelable(false)
            .setPositiveButton("Delete") { dialog, _ ->
                viewModel.deleteSkillCategory(skillCategory)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}