package com.aditya.porfiliohandler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.porfiliohandler.databinding.ItemBlogBinding
import com.aditya.porfiliohandler.domain.model.Blogs
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.Locale

class BlogsAdapter(
    private var items: List<Blogs>
) : RecyclerView.Adapter<BlogsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemBlogBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBlogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.blogTitle.text = item.title

        // Published / Draft badge
        if (item.published) {
            holder.binding.publishedBadge.visibility = View.VISIBLE
            holder.binding.draftBadge.visibility = View.GONE
        } else {
            holder.binding.publishedBadge.visibility = View.GONE
            holder.binding.draftBadge.visibility = View.VISIBLE
        }

        // Reading time
        holder.binding.blogReadingTime.text = "${item.readingTime} min read"

        // Date
        item.createdAt?.let { timestamp ->
            val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            holder.binding.blogDate.text = dateFormat.format(timestamp.toDate())
        } ?: run {
            holder.binding.blogDate.text = ""
        }

        // Tags as chips
        holder.binding.blogTagsChipGroup.removeAllViews()
        item.tags.forEach { tag ->
            val chip = Chip(holder.binding.blogTagsChipGroup.context).apply {
                text = "#$tag"
                isClickable = false
                isCheckable = false
                textSize = 11f
            }
            holder.binding.blogTagsChipGroup.addView(chip)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Blogs>) {
        items = newItems
        notifyDataSetChanged()
    }
}
