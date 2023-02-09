package com.ls.iusta.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.core.dialog.showDialog
import com.ls.iusta.databinding.FragmentNotificationsListBinding
import com.ls.iusta.domain.models.push.MainScreenUIModel
import com.ls.iusta.domain.models.push.NotificationsUIModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.notifications.NotificationsListViewModel
import com.ls.iusta.ui.MainActivity
import com.ls.iusta.ui.ticketslist.TicketsListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonCancellable.isActive
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsListFragment :
    BaseFragment<FragmentNotificationsListBinding, NotificationsListViewModel>() {

    @Inject
    lateinit var notificationsAdapter: NotificationsAdapter

    var pageNum: Int = 1
    private var isLoading: Boolean = false
    private var lastPage: Boolean = false
    private lateinit var lManager: LinearLayoutManager

    override val viewModel: NotificationsListViewModel by viewModels()

    override fun getViewBinding(): FragmentNotificationsListBinding =
        FragmentNotificationsListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.pushList, ::onViewStateChange)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            clearAdapter()
            viewModel.getPushes(pageNum)
            isLoading = true
        }

        binding.recyclerViewNotifications.apply {
            adapter = notificationsAdapter
            lManager = LinearLayoutManager(requireContext())
            layoutManager = lManager
        }

        binding.recyclerViewNotifications.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading && !lastPage) {
                    if (lManager.findLastCompletelyVisibleItemPosition() == notificationsAdapter.list.size - 1) {
                        pageNum++
                        viewModel.getPushes(pageNum)
                        isLoading = true
                    }
                }
            }
        })

        notificationsAdapter.setItemClickListener { push ->
            if (push.ticket_id != null)
                showDialog(
                    getString(R.string.app_name),
                    push.text,
                    "Ok",
                    null,
                    getString(R.string.open_ticket),
                    negativeListener = {
                        findNavController().navigate(
                            NotificationsListFragmentDirections.actionNotificationsFragmentToTicketDetailFragment(
                                push.ticket_id!!
                            )
                        )
                    })
            else
                showDialog(getString(R.string.app_name), push.text, "Ok")
            viewModel.pushesEdit(push.id.toString(), true)
        }
    }

    override fun onStop() {
        super.onStop()
        clearAdapter()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPushes(pageNum)
        isLoading = true
    }

    private fun clearAdapter(){
        pageNum = 1
        lastPage = false
        notificationsAdapter.list = emptyList()
    }

    private fun onViewStateChange(event: NotificationsUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is NotificationsUIModel.Loading -> {
                handleLoading(true)
            }
            is NotificationsUIModel.Success -> {
                handleLoading(false)
                if (event.data.success) {
                    if (event.data.pageSize < event.data.perPage)
                        lastPage = true

                    if (pageNum == 1) {
                        notificationsAdapter.list = event.data.response!!
                    } else {
                        notificationsAdapter.addItems(event.data.response!!)
                    }
                    isLoading = false
                } else {
                    event.data.message?.map {
                        handleErrorMessage(it.value.get(0))
                    }
                }
            }
            is NotificationsUIModel.Error -> {
                handleErrorMessage(event.error)
            }
            is NotificationsUIModel.Read -> {
                (requireActivity() as MainActivity).updateUserInfo()
            }
            is NotificationsUIModel.Delete -> {

            }
        }
    }


}