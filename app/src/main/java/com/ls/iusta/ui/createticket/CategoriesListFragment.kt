package com.ls.iusta.ui.createticket

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.DialogCreateTicketBinding
import com.ls.iusta.databinding.FragmentCategoriesListBinding
import com.ls.iusta.domain.models.category.Category
import com.ls.iusta.domain.models.category.CategoryInfo
import com.ls.iusta.domain.models.category.CategoryUiModel
import com.ls.iusta.extension.makeGone
import com.ls.iusta.extension.makeVisible
import com.ls.iusta.extension.observe
import com.ls.iusta.extension.showSnackBar
import com.ls.iusta.presentation.viewmodel.tickets.CategoriesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.NullPointerException
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

    private lateinit var loader:ProgressBar
    private lateinit var attachIv: ImageView
    private lateinit var attachIvRemove: ImageView

    override fun getViewBinding(): FragmentCategoriesListBinding =
        FragmentCategoriesListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sendRequest(menuId, false)
        observe(viewModel.category, ::onViewStateChange)
        setupRecyclerView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (menuId != 0) {
                    menuId = backMenuId
                    viewModel.sendRequest(menuId, false)
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
            viewModel.sendRequest(menuId, false)
        }

        binding.recyclerViewCategories.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        categoriesAdapter.setItemClickListener { category ->
            menuId = category.id
            if (category.menu) {
                viewModel.sendRequest(menuId, false)
            } else {
                showCreateDialog()
            }
        }


    }

    private fun showCreateDialog() {
        dialog = BottomSheetDialog(requireContext())
        val dialogBindig = DialogCreateTicketBinding.inflate(layoutInflater)
        loader = dialogBindig.loader
        attachIv = dialogBindig.attachment
        attachIvRemove = dialogBindig.remove
        attachIvRemove.setOnClickListener {
            attachIv.setImageURI(null)
            attachIv.setImageResource(0)
            attachIvRemove.makeGone()
        }
        dialogBindig.gallery.setOnClickListener {
            dialogBindig.loader.makeVisible()
            ImagePicker.with(requireActivity()).galleryOnly().compress(1024)
                .createIntent { intent ->
                    startForAttachmentResult.launch(intent)
                }
        }
        dialogBindig.photo.setOnClickListener {
            dialogBindig.loader.makeVisible()
            ImagePicker.with(requireActivity()).cameraOnly().compress(1024)
                .createIntent { intent ->
                    startForAttachmentResult.launch(intent)
                }
        }
        dialogBindig.pdf.setOnClickListener {
            dialogBindig.loader.makeVisible()
            selectPDF()
        }
        dialogBindig.idBtnDismiss.setOnClickListener {
            dialog.dismiss()
        }
        dialogBindig.idBtnCreate.setOnClickListener {
            viewModel.sendRequest(menuId, true)
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
                // Get PDF path
                //val realPath = FileUriUtils.getRealPath(requireContext(), fileUri)
                if (isPdf(fileUri) == true) {
                    attachIv.setImageResource(R.drawable.ic_baseline_insert_drive_file_24)
                } else {
                    attachIv.setImageURI(fileUri)
                }
                loader.makeGone()
                attachIvRemove.makeVisible()
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                showSnackBar(binding.root, ImagePicker.getError(data))
            } else {
                showSnackBar(binding.root, "Task Cancelled")
            }
        }


    private fun isPdf(uri: Uri): Boolean? {
        try {
            val mCursor: Cursor =
                requireContext().getContentResolver().query(uri, null, null, null, null)!!
            val indexedname: Int = mCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            mCursor.moveToFirst()
            val filename: String = mCursor.getString(indexedname)
            mCursor.close()
            return filename.lowercase().contains("pdf")
        } catch (e: NullPointerException) {
            return false
        }
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
                var categories = info.menus.map {
                    Category(it.id, it.name, it.description, it.icon, true)
                }
                categoriesAdapter.list = categories + info.categories
            }
            is CategoryUiModel.CreateTicketSuccess -> {
                handleLoading(false)
                dialog.hide()
            }
            is CategoryUiModel.Error -> {
                handleErrorMessage(event.error)
            }
        }
    }


}