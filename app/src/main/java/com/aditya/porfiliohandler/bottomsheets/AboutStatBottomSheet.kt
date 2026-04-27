package com.aditya.porfiliohandler.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aditya.porfiliohandler.databinding.DailogUpdateStatsBinding
import com.aditya.porfiliohandler.domain.model.Stat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AboutStatBottomSheet(
    private val stat: Stat,
    private val onUpdate: (Stat) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: DailogUpdateStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DailogUpdateStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etLabel.setText(stat.label)
        binding.etValue.setText(stat.value)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnUpdate.setOnClickListener {

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

            onUpdate(updatedStat)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}