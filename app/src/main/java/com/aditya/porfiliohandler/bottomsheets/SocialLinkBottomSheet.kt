package com.aditya.porfiliohandler.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aditya.porfiliohandler.databinding.DialogSocialLinkBinding
import com.aditya.porfiliohandler.domain.model.SocialLink
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SocialLinkBottomSheet(
    private val socialLink: SocialLink? = null,
    private val onSave: (SocialLink) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: DialogSocialLinkBinding? = null
    private val binding get() = _binding!!

    private val isEditMode get() = socialLink != null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSocialLinkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sheetTitle.text = if (isEditMode) "Update Social Link" else "Add Social Link"
        binding.btnSave.text = if (isEditMode) "Update" else "Add"

        socialLink?.let {
            binding.etPlatform.setText(it.platform)
            binding.etUrl.setText(it.url)
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val platform = binding.etPlatform.text.toString().trim()
            val url = binding.etUrl.text.toString().trim()

            if (platform.isEmpty()) {
                binding.etPlatform.error = "Required"
                return@setOnClickListener
            }
            if (url.isEmpty()) {
                binding.etUrl.error = "Required"
                return@setOnClickListener
            }

            val result = SocialLink(
                platform = platform,
                url = url
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
