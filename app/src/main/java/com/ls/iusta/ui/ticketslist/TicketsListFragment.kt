package com.ls.iusta.ui.ticketslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.config.GservicesValue.value
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentTicketsListBinding
import com.ls.iusta.domain.models.settings.SettingUiModel
import com.ls.iusta.domain.models.tickets.TicketUIModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.utils.AppConstants
import com.ls.iusta.presentation.viewmodel.tickets.TicketsListViewModel
import com.ls.iusta.ui.auth.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TicketsListFragment : BaseFragment<FragmentTicketsListBinding, TicketsListViewModel>() {

    @Inject
    lateinit var ticketAdapter: TicketAdapter

    var isActive: Boolean = true
    var pageNum: Int = 1
    private var isLoading: Boolean = false
    private var lastPage: Boolean = false
    private lateinit var lManager: LinearLayoutManager

    override val viewModel: TicketsListViewModel by viewModels()

    override fun getViewBinding(): FragmentTicketsListBinding =
        FragmentTicketsListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.ticketList, ::onViewStateChange)
        isActive =
            (findNavController().currentDestination?.label == getString(R.string.fragment_tickets_title_label))

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.apply {
            refresh.setOnRefreshListener {
                binding.refresh.isRefreshing = false
                clearAdapter()
                viewModel.getTickets(isActive, pageNum)
                isLoading = true
            }

            recyclerViewTickets.apply {
                adapter = ticketAdapter
                lManager = LinearLayoutManager(requireContext())
                layoutManager = lManager
            }

            recyclerViewTickets.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!isLoading && !lastPage) {
                        if (lManager.findLastCompletelyVisibleItemPosition() == ticketAdapter.list.size - 1) {
                            pageNum++
                            viewModel.getTickets(isActive, pageNum)
                            isLoading = true
                        }
                    }
                }
            })

            ticketAdapter.setItemClickListener { ticket ->
                findNavController().navigate(
                    TicketsListFragmentDirections.actionTicketListFragmentToTicketDetailFragment(
                        ticket.id
                    )
                )
            }

            ticketAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    lManager.scrollToPositionWithOffset(positionStart, 0)
                }
            })

            nextButton.setOnClickListener {
                findNavController().navigate(
                    TicketsListFragmentDirections.actionTicketListFragmentToCreateTicketFragment()
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        clearAdapter()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getTickets(isActive, pageNum)
        isLoading = true
    }

    private fun clearAdapter(){
        pageNum = 1
        lastPage = false
        ticketAdapter.list = emptyList()
    }

    private fun onViewStateChange(event: TicketUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is TicketUIModel.Loading -> {
                handleLoading(true)
            }
            is TicketUIModel.Success -> {
                handleLoading(false)
                if (event.data.success) {
                    if (event.data.pageSize < event.data.perPage)
                        lastPage = true

                    if (pageNum == 1) {
                        ticketAdapter.list = event.data.response!!
                    } else {
                        ticketAdapter.addItems(event.data.response!!)
                    }
                    isLoading = false
                } else {
                    event.data.message?.map {
                        if (it.key.equals(AppConstants.Message.INVALID_TOKEN)) {
                            viewModel.logout()
                        }
                        handleErrorMessage(it.value.get(0))
                    }
                }
            }
            is TicketUIModel.Error -> {
                handleErrorMessage(event.error)
            }
            is TicketUIModel.Logout -> {
                LoginActivity.startActivity(requireActivity())
            }
        }
    }

}