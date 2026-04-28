package com.aditya.porfiliohandler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.porfiliohandler.databinding.ItemExperienceBinding
import com.aditya.porfiliohandler.domain.model.Experience

class ExperienceAdapter(
    private var items: List<Experience>,
    private var onExperienceClick: (Experience) -> Unit = {},
    private var onExperienceDelete: (Experience) -> Unit = {}
) : RecyclerView.Adapter<ExperienceAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemExperienceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExperienceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.experienceRole.text = item.role
        holder.binding.experienceCompany.text = item.company

        // Build duration string
        val endText = if (item.current) "Present" else item.endDate
        holder.binding.experienceDuration.text = "${item.startDate} — $endText"

        // Description
        if (item.description.isNotEmpty()) {
            holder.binding.experienceDescription.visibility = View.VISIBLE
            holder.binding.experienceDescription.text = item.description
        } else {
            holder.binding.experienceDescription.visibility = View.GONE
        }

        // Current badge
        if (item.current) {
            holder.binding.currentBadge.visibility = View.VISIBLE
        } else {
            holder.binding.currentBadge.visibility = View.GONE
        }

        holder.binding.root.setOnClickListener {
            onExperienceClick(item)
        }

        holder.binding.deleteButton.setOnClickListener {
            onExperienceDelete(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Experience>) {
        items = newItems
        notifyDataSetChanged()
    }
}

