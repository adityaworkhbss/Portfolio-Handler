package com.aditya.porfiliohandler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.porfiliohandler.databinding.ItemStatBinding
import com.aditya.porfiliohandler.domain.model.Stat

class AboutStatsGridAdapter(
    private var items: List<Stat>,
    private val onStatsClick: (Stat) -> Unit
) : RecyclerView.Adapter<AboutStatsGridAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemStatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.statValue.text = item.label
        holder.binding.statLabel.text = item.value

        holder.binding.root.setOnClickListener {
            onStatsClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}