package com.ls.iusta.ui.createticket

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ls.iusta.BuildConfig
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.DialogCreateTicketBinding
import com.ls.iusta.databinding.FragmentCategoriesListBinding
import com.ls.iusta.databinding.FragmentTicketsListBinding
import com.ls.iusta.domain.models.auth.LoginUiModel
import com.ls.iusta.domain.models.category.Category
import com.ls.iusta.domain.models.category.CategoryInfo
import com.ls.iusta.domain.models.category.CategoryUiModel
import com.ls.iusta.domain.models.tickets.TicketUIModel
import com.ls.iusta.extension.makeVisible
import com.ls.iusta.extension.observe
import com.ls.iusta.extension.showSnackBar
import com.ls.iusta.presentation.viewmodel.CategoriesListViewModel
import com.ls.iusta.presentation.viewmodel.TicketsListViewModel
import com.ls.iusta.ui.ticketslist.TicketAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonCancellable.isActive
import timber.log.Timber
import javax.crypto.Cipher.SECRET_KEY
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesListFragment :
    BaseFragment<FragmentCategoriesListBinding, CategoriesListViewModel>() {

    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter

    override val viewModel: CategoriesListViewModel by viewModels()

    lateinit var dialog:BottomSheetDialog

    private var menuId: Int = 0

    override fun getViewBinding(): FragmentCategoriesListBinding =
        FragmentCategoriesListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sendRequest(menuId, false)
        observe(viewModel.category, ::onViewStateChange)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            //  viewModel.getCategoryInfo(menuId)
        }

        binding.recyclerViewCategories.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        categoriesAdapter.setItemClickListener { category ->
            menuId = category.id
            if (category.menu) {
                binding.currentCategory.apply {
                    makeVisible()
                    text = category.name
                }
                viewModel.sendRequest(menuId, false)
            } else {
//                viewModel.createTicket()
                showCreateDialog()
            }
        }


    }

    private fun showCreateDialog() {
        dialog = BottomSheetDialog(requireContext())
        val dialogBindig = DialogCreateTicketBinding.inflate(layoutInflater)
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


    private fun onViewStateChange(event: CategoryUiModel) {
        if (event.isRedelivered) return
        when (event) {
            is CategoryUiModel.Loading -> {
                handleLoading(true)
            }
            is CategoryUiModel.LoadCategoriesSuccess -> {
                handleLoading(false)
                val info: CategoryInfo = event.data
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