package com.aditya.porfiliohandler.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aditya.porfiliohandler.databinding.DialogSkillCategoryBinding
import com.aditya.porfiliohandler.domain.model.SkillCategory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SkillCategoryBottomSheet(
    private val skillCategory: SkillCategory? = null,
    private val onSave: (SkillCategory) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: DialogSkillCategoryBinding? = null
    private val binding get() = _binding!!

    private val isEditMode get() = skillCategory != null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSkillCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sheetTitle.text = if (isEditMode) "Update Skill Category" else "Add Skill Category"
        binding.btnSave.text = if (isEditMode) "Update" else "Add"

        skillCategory?.let {
            binding.etCategory.setText(it.category)
            binding.etSkills.setText(it.items.joinToString(", "))
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val category = binding.etCategory.text.toString().trim()
            val skillsText = binding.etSkills.text.toString().trim()

            if (category.isEmpty()) {
                binding.etCategory.error = "Required"
                return@setOnClickListener
            }
            if (skillsText.isEmpty()) {
                binding.etSkills.error = "Required"
                return@setOnClickListener
            }

            val skillsList = skillsText.split(",").map { it.trim() }.filter { it.isNotEmpty() }

            val result = SkillCategory(
                category = category,
                items = skillsList
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
