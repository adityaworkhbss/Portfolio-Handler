package com.aditya.porfiliohandler.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aditya.porfiliohandler.databinding.DialogProjectBinding
import com.aditya.porfiliohandler.domain.model.Projects
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProjectBottomSheet(
    private val project: Projects? = null,
    private val onSave: (Projects) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: DialogProjectBinding? = null
    private val binding get() = _binding!!

    private val isEditMode get() = project != null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogProjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sheetTitle.text = if (isEditMode) "Edit Project" else "Add Project"
        binding.btnSave.text = if (isEditMode) "Update" else "Add"

        project?.let {
            binding.etTitle.setText(it.title)
            binding.etDescription.setText(it.description)
            binding.etLongDescription.setText(it.longDescription)
            binding.etCoverImage.setText(it.coverImage)
            binding.etGithubUrl.setText(it.githubUrl)
            binding.etLiveUrl.setText(it.liveUrl)
            binding.etTechStack.setText(it.techStack.joinToString(", "))
            binding.etOrder.setText(it.order.toString())
            binding.switchFeatured.isChecked = it.featured
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()
            val longDescription = binding.etLongDescription.text.toString().trim()
            val coverImage = binding.etCoverImage.text.toString().trim()
            val githubUrl = binding.etGithubUrl.text.toString().trim()
            val liveUrl = binding.etLiveUrl.text.toString().trim()
            val techStackText = binding.etTechStack.text.toString().trim()
            val orderText = binding.etOrder.text.toString().trim()
            val featured = binding.switchFeatured.isChecked

            if (title.isEmpty()) {
                binding.etTitle.error = "Required"
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                binding.etDescription.error = "Required"
                return@setOnClickListener
            }

            val techStack = techStackText.split(",").map { it.trim() }.filter { it.isNotEmpty() }
            val order = orderText.toIntOrNull() ?: 0

            val result = Projects(
                id = project?.id ?: "",
                title = title,
                description = description,
                longDescription = longDescription,
                coverImage = coverImage,
                githubUrl = githubUrl,
                liveUrl = liveUrl,
                featured = featured,
                order = order,
                techStack = techStack,
                images = project?.images ?: emptyList(),
                createdAt = project?.createdAt,
                updatedAt = project?.updatedAt
            )

            onSave(result)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
