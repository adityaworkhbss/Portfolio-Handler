package com.aditya.porfiliohandler.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.porfiliohandler.databinding.ItemProjectBinding
import com.aditya.porfiliohandler.domain.model.Projects
import com.google.android.material.chip.Chip

class ProjectsAdapter(
    private var items: List<Projects>,
    private var onClickProjects: (Projects) -> Unit,
    private var onClickDeleteProjects: (Projects) -> Unit,
) : RecyclerView.Adapter<ProjectsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProjectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.projectTitle.text = item.title
        holder.binding.projectDescription.text = item.description

        // Featured badge
        if (item.featured) {
            holder.binding.featuredBadge.visibility = View.VISIBLE
        } else {
            holder.binding.featuredBadge.visibility = View.GONE
        }

        // Tech Stack chips
        holder.binding.techStackChipGroup.removeAllViews()
        item.techStack.forEach { tech ->
            val chip = Chip(holder.binding.techStackChipGroup.context).apply {
                text = tech
                isClickable = false
                isCheckable = false
                textSize = 11f
            }
            holder.binding.techStackChipGroup.addView(chip)
        }

        // GitHub button
        if (item.githubUrl.isNotEmpty()) {
            holder.binding.githubButton.visibility = View.VISIBLE
            holder.binding.githubButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.githubUrl))
                it.context.startActivity(intent)
            }
        } else {
            holder.binding.githubButton.visibility = View.GONE
        }

        // Live URL button
        if (item.liveUrl.isNotEmpty()) {
            holder.binding.liveButton.visibility = View.VISIBLE
            holder.binding.liveButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.liveUrl))
                it.context.startActivity(intent)
            }
        } else {
            holder.binding.liveButton.visibility = View.GONE
        }

        holder.binding.editButton.setOnClickListener {
            onClickProjects(item)
        }

        holder.binding.deleteButton.setOnClickListener {
            onClickDeleteProjects(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Projects>) {
        items = newItems
        notifyDataSetChanged()
    }
}
