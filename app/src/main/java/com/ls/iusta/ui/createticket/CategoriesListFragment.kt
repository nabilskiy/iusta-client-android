package com.ls.iusta.ui.createticket

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUriUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.DialogCreateTicketBinding
import com.ls.iusta.databinding.FragmentCategoriesListBinding
import com.ls.iusta.domain.models.category.Category
import com.ls.iusta.domain.models.category.CategoryInfo
import com.ls.iusta.domain.models.category.CategoryUiModel
import com.ls.iusta.domain.models.tickets.AttachmentFile
import com.ls.iusta.extension.observe
import com.ls.iusta.extension.showSnackBar
import com.ls.iusta.presentation.viewmodel.tickets.CategoriesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.CharacterIterator
import java.text.StringCharacterIterator
import javax.inject.Inject


@AndroidEntryPoint
class CategoriesListFragment :
    BaseFragment<FragmentCategoriesListBinding, CategoriesListViewModel>() {

    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter

    override val viewModel: CategoriesListViewModel by viewModels()

    lateinit var dialog: BottomSheetDialog
    private var menuId: Int = 0
    private var backMenuId: Int = 0

    private lateinit var attachList: RecyclerView
    private lateinit var sizeTv: TextView
    var attachmentSize: Long = 0L

    @Inject
    lateinit var attachmentsAdapter: AttachmentsAdapter
    private val attachmentFilesList = mutableListOf<AttachmentFile>()
    private val attachmentsList = mutableListOf<AttachmentPreview>()

    override fun getViewBinding(): FragmentCategoriesListBinding =
        FragmentCategoriesListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCategories(menuId)
        observe(viewModel.category, ::onViewStateChange)
        viewModel.orderNoteAttachmentSize.observe(viewLifecycleOwner) { size ->
            sizeTv.text = getString(R.string.total_size) + humanReadableByteCountBin(size)
            attachmentSize = size
        }
        setupRecyclerView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (menuId != 0) {
                    menuId = backMenuId
                    viewModel.getCategories(menuId)
                } else {
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            viewModel.getCategories(menuId)
        }

        binding.recyclerViewCategories.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        categoriesAdapter.setItemClickListener { category ->
            menuId = category.id
            if (category.menu) {
                viewModel.getCategories(menuId)
            } else {
                showCreateDialog()
            }
        }

        attachmentsAdapter.setItemClickListener { attach ->
            viewModel.attachmentDeleteSize(attach.size)
            attachmentsList.remove(attach)
            attachmentFilesList.map {
                if (it.size == attach.size)
                    attachmentFilesList.remove(it)
            }
            attachmentsAdapter.notifyDataSetChanged()
        }
    }

    private fun showCreateDialog() {
        dialog = BottomSheetDialog(requireContext())
        val dialogBindig = DialogCreateTicketBinding.inflate(layoutInflater)
        attachList = dialogBindig.attachments
        sizeTv = dialogBindig.size
        attachList.apply {
            adapter = attachmentsAdapter
        }

        dialogBindig.gallery.setOnClickListener {
            ImagePicker.with(requireActivity()).galleryOnly().compress(1024)
                .createIntent { intent ->
                    startForAttachmentResult.launch(intent)
                }
        }
        dialogBindig.photo.setOnClickListener {
            ImagePicker.with(requireActivity()).cameraOnly().compress(1024)
                .createIntent { intent ->
                    startForAttachmentResult.launch(intent)
                }
        }
        dialogBindig.pdf.setOnClickListener {
            selectPDF()
        }
        dialogBindig.idBtnDismiss.setOnClickListener {
            viewModel.attachmentSizeClear()
            attachmentFilesList.clear()
            attachmentsList.clear()
            attachmentsAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        dialogBindig.idBtnCreate.setOnClickListener {
            viewModel.sendTicket(
                attachmentFilesList,
                menuId,
                dialogBindig.note.text.toString(),
                attachmentSize
            )
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(dialogBindig.root)
        dialog.show()
    }

    private fun selectPDF() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        startForAttachmentResult.launch(intent)
    }

    private val startForAttachmentResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                handleSelectImage(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                showSnackBar(binding.root, ImagePicker.getError(data))
            } else {
                showSnackBar(binding.root, "Task Cancelled")
            }
        }

    @SuppressLint("Range")
    private fun handleSelectImage(uri: Uri?) {
        if (uri != null) {
            val cursor = context?.contentResolver?.query(uri, null, null, null, null)
            cursor?.moveToFirst()
            val name =
                cursor?.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
            val size = cursor?.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE))
            val realPath = FileUriUtils.getRealPath(requireContext(), uri)
            attachmentFilesList.add(
                AttachmentFile(
                    name,
                    size,
                    File(realPath)
                )
            )
            size?.let { AttachmentPreview(it, uri) }?.let { attachmentsList.add(it) }
            attachmentsAdapter.list = attachmentsList
            attachmentsAdapter.notifyDataSetChanged()
            if (size != null) {
                viewModel.attachmentResize(size)
            }
        }
    }

    fun humanReadableByteCountBin(bytes: Long): String? {
        val absB = if (bytes == Long.MIN_VALUE) Long.MAX_VALUE else Math.abs(bytes)
        if (absB < 1024) {
            return "$bytes B"
        }
        var value = absB
        val ci: CharacterIterator = StringCharacterIterator("KMGTPE")
        var i = 40
        while (i >= 0 && absB > 0xfffccccccccccccL shr i) {
            value = value shr 10
            ci.next()
            i -= 10
        }
        value *= java.lang.Long.signum(bytes).toLong()
        return java.lang.String.format("%.1f %cB", value / 1024.0, ci.current())
    }

    private fun onViewStateChange(event: CategoryUiModel) {
        if (event.isRedelivered) return
        when (event) {
            is CategoryUiModel.Loading -> {
                handleLoading(true)
            }
            is CategoryUiModel.LoadCategoriesSuccess -> {
                handleLoading(false)
                val info: CategoryInfo = event.data
                backMenuId = info.back_menu
                val categories = info.menus.map {
                    Category(it.id, it.name, it.description, it.icon, true)
                }
                categoriesAdapter.list = categories + info.categories
            }
            is CategoryUiModel.CreateTicketSuccess -> {
                handleLoading(false)
                if (event.data.success)
                    dialog.hide()
                else
                    event.data.message?.map {
                        handleErrorMessage(it.value.get(0))
                    }
            }
            is CategoryUiModel.NoteError -> {
                if (event.error) {
                    handleLoading(false)
                    handleErrorMessage(getString(R.string.empty_note))
                }
            }
            is CategoryUiModel.SizeError -> {
                if (event.error) {
                    handleLoading(false)
                    handleErrorMessage(getString(R.string.big_size))
                }
            }
            is CategoryUiModel.Error -> {
                handleErrorMessage(event.error)
            }
        }
    }
}