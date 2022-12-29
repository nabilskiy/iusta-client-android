package com.ls.iusta.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.core.dialog.showDialog
import com.ls.iusta.databinding.FragmentNotificationsListBinding
import com.ls.iusta.domain.models.push.MainScreenUIModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.notifications.NotificationsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsListFragment : BaseFragment<FragmentNotificationsListBinding, NotificationsListViewModel>() {

    @Inject
    lateinit var notificationsAdapter: NotificationsAdapter

    override val viewModel: NotificationsListViewModel by viewModels()

    override fun getViewBinding(): FragmentNotificationsListBinding =
        FragmentNotificationsListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPushes()
        observe(viewModel.pushList, ::onViewStateChange)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            viewModel.getPushes()
        }

        binding.recyclerViewTickets.apply {
            adapter = notificationsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        notificationsAdapter.setItemClickListener { push ->
            showDialog(getString(R.string.app_name), push.text, "Ok")
//            findNavController().navigate(
//                NotificationsListFragmentDirections.actionNotificationsFragmentToTicketDetailFragmentt(
//                    push.worker_id.toLong()
//                )
//            )
        }


    }

    private fun onViewStateChange(event: MainScreenUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is MainScreenUIModel.Loading -> {
                handleLoading(true)
            }
            is MainScreenUIModel.Success -> {
                handleLoading(false)
                notificationsAdapter.list = event.data
            }
            is MainScreenUIModel.Error -> {
                handleErrorMessage(event.error)
            }
        }
    }


}