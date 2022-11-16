package com.ls.iusta.ui.ticketslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentTicketsListBinding
import com.ls.iusta.domain.models.TicketUIModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.TicketsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TicketsListFragment : BaseFragment<FragmentTicketsListBinding, TicketsListViewModel>() {

    @Inject
    lateinit var ticketAdapter: TicketAdapter

    override val viewModel: TicketsListViewModel by viewModels()

    override fun getViewBinding(): FragmentTicketsListBinding =
        FragmentTicketsListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isFavorite =
            (findNavController().currentDestination?.label == getString(R.string.menu_history))
        viewModel.getTickets(isFavorite)
        observe(viewModel.ticketList, ::onViewStateChange)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewCharacters.apply {
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