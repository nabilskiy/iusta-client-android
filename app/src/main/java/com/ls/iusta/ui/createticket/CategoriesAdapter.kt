package com.ls.iusta.ui.createticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ls.iusta.base.BaseAdapter
import com.ls.iusta.databinding.ItemCategoryListBinding
import com.ls.iusta.databinding.ItemTicketListBinding
import com.ls.iusta.domain.models.category.Category
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.ui.ticketslist.TicketAdapter
import javax.inject.Inject

class CategoriesAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseAdapter<Category>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.hashCode() == newItem.hashCode()

    }

    override val differ: AsyncListDiffer<Category> = AsyncListDiffer(this, diffCallback)

    override
    fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemCategoryListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CategoryViewHolder(binding)
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryListBinding) :
        RecyclerView.ViewHolder(binding.root), Binder<Category> {
        override fun bind(item: Category) {
            binding.apply {
                categoryName.text = item.name
               // categoryDescr.text = item.description
                glide.load(item.icon).into(icon)

                root.setOnClickListener {
                    onItemClickListener?.let { itemClick ->
                        itemClick(item)
                    }
                }
            }
        }
    }
}