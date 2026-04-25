package com.aditya.porfiliohandler.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.porfiliohandler.databinding.ItemMessageBinding
import com.aditya.porfiliohandler.domain.model.Messages
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.core.net.toUri

class MessagesAdapter(
    private var items: List<Messages>,
    private val onItemDeleteClick: (Messages) -> Unit,
    private val onItemReadClick:(Messages) ->Unit
) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.messageSenderName.text = item.name
        holder.binding.messageEmail.text = item.email
        holder.binding.messagePreview.text = item.message

        if (!item.read) {
            holder.binding.newBadge.visibility = View.VISIBLE
            holder.binding.unreadDot.visibility = View.VISIBLE
        } else {
            holder.binding.newBadge.visibility = View.GONE
            holder.binding.unreadDot.visibility = View.GONE
            holder.binding.visitedMessage.text = "Visited"
            holder.binding.visitedMessage.isClickable = false
        }

        item.createdAt?.let { timestamp ->
            val dateFormat = SimpleDateFormat("MMM dd, yyyy, hh:mm a", Locale.getDefault())
            holder.binding.messageTimestamp.text = dateFormat.format(timestamp.toDate())
        } ?: run {
            holder.binding.messageTimestamp.text = ""
        }

        holder.binding.deleteMessageButton.setOnClickListener {
            onItemDeleteClick(
                item
            )
        }

        holder.binding.respondMessage.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:${item.email}".toUri()
                putExtra(Intent.EXTRA_SUBJECT, "Response to your message")
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.binding.visitedMessage.setOnClickListener {
            onItemReadClick(
                item
            )
        }


    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Messages>) {
        items = newItems
        notifyDataSetChanged()
    }
}
