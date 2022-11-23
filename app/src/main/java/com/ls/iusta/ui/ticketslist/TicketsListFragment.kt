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
import timber.log.Timber
import javax.crypto.Cipher.SECRET_KEY
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

        val isActive =
            (findNavController().currentDestination?.label == getString(R.string.menu_home))
        viewModel.getTickets(isActive)
        observe(viewModel.ticketList, ::onViewStateChange)
        setupRecyclerView()


        viewModel.startLogin("alex_n@alex_n.com","BZ67BeW4HwVqwBu", BuildConfig.SECRETKEY)
        observe(viewModel.loginData, ::onLogin)

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

    private fun onLogin(event: LoginUiModel) {
        if (event.isRedelivered) return
        when (event) {
            is LoginUiModel.Loading -> {
                handleLoading(true)
            }
            is LoginUiModel.Success -> {
                handleLoading(false)
                Timber.e(event.data.auth_token)
            }
            is LoginUiModel.Error -> {
                handleErrorMessage(event.error)
            }
        }
    }
}