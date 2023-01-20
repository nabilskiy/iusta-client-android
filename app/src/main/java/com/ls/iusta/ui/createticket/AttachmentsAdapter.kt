package com.ls.iusta.ui.createticket

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ls.iusta.R
import com.ls.iusta.base.BaseAdapter
import com.ls.iusta.databinding.ItemAttachListBinding
import com.ls.iusta.databinding.ItemCategoryListBinding
import com.ls.iusta.databinding.ItemTicketListBinding
import com.ls.iusta.domain.models.category.Category
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.ui.ticketslist.AttachmentPreview
import com.ls.iusta.ui.ticketslist.TicketAdapter
import java.io.File
import java.lang.NullPointerException
import javax.inject.Inject

class AttachmentsAdapter @Inject constructor(
) : BaseAdapter<AttachmentPreview>() {

    private val diffCallback = object : DiffUtil.ItemCallback<AttachmentPreview>() {
        override fun areItemsTheSame(
            oldItem: AttachmentPreview,
            newItem: AttachmentPreview
        ): Boolean =
            oldItem.size == newItem.size

        override fun areContentsTheSame(
            oldItem: AttachmentPreview,
            newItem: AttachmentPreview
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    override val differ: AsyncListDiffer<AttachmentPreview> = AsyncListDiffer(this, diffCallback)

    override
    fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemAttachListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return AttachViewHolder(binding, parent.context)
    }

    inner class AttachViewHolder(
        private val binding: ItemAttachListBinding,
        private val ctx: Context
    ) :
        RecyclerView.ViewHolder(binding.root), Binder<AttachmentPreview> {
        override fun bind(item: AttachmentPreview) {
            binding.apply {
                if (isPdf(item.uri, ctx) == true) {
                    icon.setImageResource(R.drawable.ic_baseline_insert_drive_file_24)
                } else {
                    icon.setImageURI(item.uri)
                }
                root.setOnClickListener {
                    onItemClickListener?.let { itemClick ->
                        itemClick(item)
                    }
                }
            }
        }
    }

    private fun isPdf(uri: Uri, ctx: Context): Boolean? {
        try {
            val mCursor: Cursor =
                ctx.getContentResolver().query(uri, null, null, null, null)!!
            val indexedname: Int = mCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            mCursor.moveToFirst()
            val filename: String = mCursor.getString(indexedname)
            mCursor.close()
            return filename.lowercase().contains("pdf")
        } catch (e: NullPointerException) {
            return false
        }
    }
}
