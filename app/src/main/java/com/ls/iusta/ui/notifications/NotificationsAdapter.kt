package com.ls.iusta.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ls.iusta.base.BaseAdapter
import com.ls.iusta.databinding.ItemPushListBinding
import com.ls.iusta.databinding.ItemTicketListBinding
import com.ls.iusta.domain.models.push.Push
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.ui.ticketslist.TicketAdapter
import javax.inject.Inject

class NotificationsAdapter @Inject constructor(

) : BaseAdapter<Push>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Push>() {
        override fun areItemsTheSame(oldItem: Push, newItem: Push): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Push, newItem: Push): Boolean =
            oldItem.hashCode() == newItem.hashCode()

    }

    override val differ: AsyncListDiffer<Push> = AsyncListDiffer(this, diffCallback)

    override
    fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemPushListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PushViewHolder(binding)
    }

    inner class PushViewHolder(private val binding: ItemPushListBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<Push> {
        override fun bind(item: Push) {
            binding.apply {
                textViewTitle.text = item.title

                root.setOnClickListener {
                    onItemClickListener?.let { itemClick ->
                        itemClick(item)
                    }
                }
            }
        }
    }
}