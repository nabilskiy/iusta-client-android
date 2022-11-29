package com.ls.iusta.ui.ticketslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ls.iusta.BuildConfig
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentTicketsListBinding
import com.ls.iusta.domain.models.auth.LoginUiModel
import com.ls.iusta.domain.models.tickets.TicketUIModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.TicketsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonCancellable.isActive
import timber.log.Timber
import javax.crypto.Cipher.SECRET_KEY
import javax.inject.Inject

@AndroidEntryPoint
class TicketsListFragment : BaseFragment<FragmentTicketsListBinding, TicketsListViewModel>() {

    @Inject
    lateinit var ticketAdapter: TicketAdapter

    var isActive: Boolean = true

    override val viewModel: TicketsListViewModel by viewModels()

    override fun getViewBinding(): FragmentTicketsListBinding =
        FragmentTicketsListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isActive =
            (findNavController().currentDestination?.label == getString(R.string.fragment_tickets_title_label))
        viewModel.getTickets(isActive)
        observe(viewModel.ticketList, ::onViewStateChange)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            viewModel.getTickets(isActive)
        }

        binding.recyclerViewTickets.apply {
            adapter = ticketAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        ticketAdapter.setItemClickListener { ticket ->
            findNavController().navigate(
                TicketsListFragmentDirections.actionTicketListFragmentToTicketDetailFragment(
                    ticket.id.toLong()
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
                ticketAdapter.list = event.data
            }
            is TicketUIModel.Error -> {
                handleErrorMessage(event.error)
            }
        }
    }


}