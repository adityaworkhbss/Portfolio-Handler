package com.aditya.porfiliohandler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.porfiliohandler.databinding.ItemSocialLinkBinding
import com.aditya.porfiliohandler.domain.model.SocialLink

class AboutSocialAdapter(
    private var items: List<SocialLink>,
) : RecyclerView.Adapter<AboutSocialAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemSocialLinkBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSocialLinkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.socialName.text = item.platform
        holder.binding.socialUrl.text = item.url
    }

    override fun getItemCount(): Int {
        return items.size
    }

}