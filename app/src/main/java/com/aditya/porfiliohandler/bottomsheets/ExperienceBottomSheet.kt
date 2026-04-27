package com.aditya.porfiliohandler.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aditya.porfiliohandler.databinding.DialogExperienceBinding
import com.aditya.porfiliohandler.domain.model.Experience
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExperienceBottomSheet(
    private val experience: Experience? = null,
    private val onSave: (Experience) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: DialogExperienceBinding? = null
    private val binding get() = _binding!!

    private val isEditMode get() = experience != null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogExperienceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sheetTitle.text = if (isEditMode) "Edit Experience" else "Add Experience"
        binding.btnSave.text = if (isEditMode) "Update" else "Add"

        experience?.let {
            binding.etRole.setText(it.role)
            binding.etCompany.setText(it.company)
            binding.etDescription.setText(it.description)
            binding.etStartDate.setText(it.startDate)
            binding.etEndDate.setText(it.endDate)
            binding.switchCurrent.isChecked = it.current
            binding.etTechStack.setText(it.techStack.joinToString(", "))
            binding.etOrder.setText(it.order.toString())
        }

        // Toggle end date field based on "current" switch
        binding.switchCurrent.setOnCheckedChangeListener { _, isChecked ->
            binding.etEndDateLayout.isEnabled = !isChecked
            if (isChecked) {
                binding.etEndDate.setText("")
            }
        }

        // Apply initial state
        binding.etEndDateLayout.isEnabled = !(experience?.current ?: false)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val role = binding.etRole.text.toString().trim()
            val company = binding.etCompany.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()
            val startDate = binding.etStartDate.text.toString().trim()
            val endDate = binding.etEndDate.text.toString().trim()
            val current = binding.switchCurrent.isChecked
            val techStackText = binding.etTechStack.text.toString().trim()
            val orderText = binding.etOrder.text.toString().trim()

            if (role.isEmpty()) {
                binding.etRole.error = "Required"
                return@setOnClickListener
            }
            if (company.isEmpty()) {
                binding.etCompany.error = "Required"
                return@setOnClickListener
            }
            if (startDate.isEmpty()) {
                binding.etStartDate.error = "Required"
                return@setOnClickListener
            }
            if (!current && endDate.isEmpty()) {
                binding.etEndDate.error = "Required (or mark as current)"
                return@setOnClickListener
            }

            val techStack = techStackText.split(",").map { it.trim() }.filter { it.isNotEmpty() }
            val order = orderText.toIntOrNull() ?: 0

            val result = Experience(
                id = experience?.id ?: "",
                role = role,
                company = company,
                description = description,
                startDate = startDate,
                endDate = endDate,
                current = current,
                order = order,
                techStack = techStack
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
