package com.ls.iusta.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.ls.iusta.R
import com.ls.iusta.base.BaseAdapter
import com.ls.iusta.databinding.ItemAboutListBinding
import com.ls.iusta.databinding.ItemSettingListBinding
import com.ls.iusta.domain.models.info.About
import com.ls.iusta.domain.models.settings.SettingType
import com.ls.iusta.domain.models.settings.Settings
import com.ls.iusta.extension.makeGone
import com.ls.iusta.extension.makeVisible
import javax.inject.Inject

private const val TOP = 0
private const val MIDDLE = 1
private const val BOTTOM = 2

class AboutAdapter @Inject constructor() : BaseAdapter<About>() {

    private val diffCallback = object : DiffUtil.ItemCallback<About>() {
        override fun areItemsTheSame(oldItem: About, newItem: About): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: About, newItem: About): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    override val differ: AsyncListDiffer<About> = AsyncListDiffer(this, diffCallback)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemAboutListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AboutViewHolder(binding, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TOP
            list.size - 1 -> BOTTOM
            else -> MIDDLE
        }
    }

    inner class AboutViewHolder(
        private val binding: ItemAboutListBinding,
        private val viewType: Int
    ) : RecyclerView.ViewHolder(binding.root), Binder<About> {

        override fun bind(item: About) {
            binding.apply {
                textViewSettingName.text = item.name
                textViewSettingDescription.text = item.value
                root.setOnClickListener {
                    setClickListener(item)
                }
            }

            when (viewType) {
                TOP -> setRadius(binding.cardViewRoot, true)
                BOTTOM -> setRadius(binding.cardViewRoot, false)
            }
        }

        private fun setClickListener(item: About) {
            onItemClickListener?.let {
                it(item)
            }
        }

        private fun setRadius(cardView: MaterialCardView, isTop: Boolean) {
            val radius: Float = cardView.context.resources.getDimension(R.dimen.card_view_radius)
            val shareAppendable = cardView.shapeAppearanceModel.toBuilder().apply {
                if (isTop) {
                    setTopLeftCorner(CornerFamily.ROUNDED, radius)
                    setTopRightCorner(CornerFamily.ROUNDED, radius)
                } else {
                    setBottomRightCorner(CornerFamily.ROUNDED, radius)
                    setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                }
            }.build()
            cardView.shapeAppearanceModel = shareAppendable
        }
    }
}