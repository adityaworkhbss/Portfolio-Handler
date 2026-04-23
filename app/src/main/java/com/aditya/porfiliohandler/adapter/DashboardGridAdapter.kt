package com.aditya.porfiliohandler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.porfiliohandler.databinding.DashboardGridBinding
import com.aditya.porfiliohandler.presenter.ui.main.DashboardItem

class DashboardGridAdapter(
    private var items: List<DashboardItem>,
    private val onItemClick: (DashboardItem) -> Unit
) : RecyclerView.Adapter<DashboardGridAdapter.ViewHolder>() {

    class ViewHolder(val binding: DashboardGridBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DashboardGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.title.text = item.title
        holder.binding.titleCount.text = item.count.toString()

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<DashboardItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}