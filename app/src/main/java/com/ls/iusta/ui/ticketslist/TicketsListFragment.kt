package com.ls.iusta.ui.ticketslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentTicketsListBinding
import com.ls.iusta.domain.models.tickets.TicketUIModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.tickets.TicketsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TicketsListFragment : BaseFragment<FragmentTicketsListBinding, TicketsListViewModel>() {

    @Inject
    lateinit var ticketAdapter: TicketAdapter

    var isActive: Boolean = true
    var pageNum: Int = 1
    private var isLoading: Boolean = false
    private lateinit var lManager: LinearLayoutManager

    override val viewModel: TicketsListViewModel by viewModels()

    override fun getViewBinding(): FragmentTicketsListBinding =
        FragmentTicketsListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isActive =
            (findNavController().currentDestination?.label == getString(R.string.fragment_tickets_title_label))
        viewModel.getTickets(isActive, pageNum)
        observe(viewModel.ticketList, ::onViewStateChange)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.apply {
            refresh.setOnRefreshListener {
                binding.refresh.isRefreshing = false
                pageNum = 1
                viewModel.getTickets(isActive, pageNum)
            }

            recyclerViewTickets.apply {
                adapter = ticketAdapter
                lManager = LinearLayoutManager(requireContext())
                layoutManager = lManager
            }

            recyclerViewTickets.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!isLoading) {
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

    private fun onViewStateChange(event: TicketUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is TicketUIModel.Loading -> {
                handleLoading(true)
            }
            is TicketUIModel.Success -> {
                handleLoading(false)
                // val tempList = ticketAdapter.list.toMutableList()
                //tempList.addAll(event.data)
                ticketAdapter.list = event.data
            }
            is TicketUIModel.Error -> {
                handleErrorMessage(event.error)
            }
        }
    }

}