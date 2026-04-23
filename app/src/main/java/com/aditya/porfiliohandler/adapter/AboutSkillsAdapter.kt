package com.aditya.porfiliohandler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.porfiliohandler.databinding.ItemSkillCategoryBinding
import com.aditya.porfiliohandler.domain.model.SkillCategory
import com.google.android.material.chip.Chip

class AboutSkillsAdapter(
    private var items : List<SkillCategory>,
) : RecyclerView.Adapter<AboutSkillsAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemSkillCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSkillCategoryBinding.inflate(
            android.view.LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.categoryName.text = item.category
        val chipGroup = holder.binding.skillChipGroup
        chipGroup.removeAllViews()

        item.items.forEach { skill ->
            val chip = Chip(chipGroup.context).apply {
                text = skill
                isClickable = false
                isCheckable = false
            }
            chipGroup.addView(chip)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}