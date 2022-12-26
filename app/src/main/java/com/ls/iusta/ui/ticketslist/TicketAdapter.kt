package com.ls.iusta.ui.ticketslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ls.iusta.base.BaseAdapter
import com.ls.iusta.databinding.ItemTicketList2Binding
import com.ls.iusta.databinding.ItemTicketListBinding
import com.ls.iusta.domain.models.tickets.Ticket
import javax.inject.Inject

class TicketAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseAdapter<Ticket>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Ticket>() {
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean =
            oldItem.hashCode() == newItem.hashCode()

    }

    override val differ: AsyncListDiffer<Ticket> = AsyncListDiffer(this, diffCallback)

    override
    fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemTicketList2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TicketViewHolder(binding)
    }

    inner class TicketViewHolder(private val binding: ItemTicketList2Binding) :
        RecyclerView.ViewHolder(binding.root), Binder<Ticket> {
        override fun bind(item: Ticket) {
            binding.apply {
                textViewTicketName.text = item.category_name
                textViewStatus.text = item.current_event_label
                textViewDate.text = item.create_time
                glide.load("https://i-usta.com/storage/categories/projector.png").circleCrop()
                    .into(icon)
                root.setOnClickListener {
                    onItemClickListener?.let { itemClick ->
                        itemClick(item)
                    }
                }
            }
        }
    }
}