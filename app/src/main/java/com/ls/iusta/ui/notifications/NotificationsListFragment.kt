package com.ls.iusta.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentNotificationsListBinding
import com.ls.iusta.databinding.FragmentTicketsListBinding
import com.ls.iusta.domain.models.tickets.TicketUIModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.notifications.NotificationsListViewModel
import com.ls.iusta.presentation.viewmodel.tickets.TicketsListViewModel
import com.ls.iusta.ui.ticketslist.TicketAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsListFragment : BaseFragment<FragmentNotificationsListBinding, NotificationsListViewModel>() {

    @Inject
    lateinit var notificationsAdapter: NotificationsAdapter

    var isActive: Boolean = true

    override val viewModel: NotificationsListViewModel by viewModels()

    override fun getViewBinding(): FragmentNotificationsListBinding =
        FragmentNotificationsListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTickets(true)
        observe(viewModel.ticketList, ::onViewStateChange)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            viewModel.getTickets(isActive)
        }

        binding.recyclerViewTickets.apply {
            adapter = notificationsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        notificationsAdapter.setItemClickListener { ticket ->
            findNavController().navigate(
                NotificationsListFragmentDirections.actionNotificationsFragmentToTicketDetailFragmentt(
                    ticket.id
                )
            )
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
                notificationsAdapter.list = event.data
            }
            is TicketUIModel.Error -> {
                handleErrorMessage(event.error)
            }
        }
    }


}