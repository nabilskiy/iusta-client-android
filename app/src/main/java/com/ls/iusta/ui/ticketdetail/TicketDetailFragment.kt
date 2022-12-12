package com.ls.iusta.ui.ticketdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentTicketDetailBinding
import com.ls.iusta.domain.models.tickets.TicketDetailUIModel
import com.ls.iusta.extension.makeVisible
import com.ls.iusta.extension.observe
import com.ls.iusta.extension.showSnackBar
import com.ls.iusta.presentation.managers.PermissionManager
import com.ls.iusta.presentation.utils.AppConstants
import com.ls.iusta.presentation.viewmodel.TicketDetailViewModel
import com.ls.iusta.ui.qrscanner.ScannerActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonCancellable.isActive
import javax.inject.Inject

@AndroidEntryPoint
class TicketDetailFragment :
    BaseFragment<FragmentTicketDetailBinding, TicketDetailViewModel>() {

    override val viewModel: TicketDetailViewModel by viewModels()
    override fun getViewBinding(): FragmentTicketDetailBinding =
        FragmentTicketDetailBinding.inflate(layoutInflater)

    private val args: TicketDetailFragmentArgs by navArgs()

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var permissionManager: PermissionManager

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                // Handle the Intent
                showSnackBar(binding.root, intent?.getStringExtra("ticket_id").toString())
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.ticketDetail, ::onViewStateChange)

        viewModel.getTicketDetail(args.ticketId)
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            viewModel.getTicketDetail(args.ticketId)
        }
        binding.scanButton.setOnClickListener {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            when {
                permissionManager.checkAllGranted(grantResults) -> {
                    startScan()
                }
            }
        }
    }

    private fun requestPermissions() {
        val result = permissionManager.cameraPermission().requestFor(requireActivity())
        if (result == PermissionManager.PERMISSIONS_GRANTED) startScan()
    }

    private fun startScan() {
        startForResult.launch(Intent(requireContext(), ScannerActivity::class.java))

    }

    private fun onViewStateChange(result: TicketDetailUIModel) {
        if (result.isRedelivered) return
        when (result) {
            is TicketDetailUIModel.Loading -> {
                handleLoading(true)
            }
            is TicketDetailUIModel.Success -> {
                handleLoading(false)
                result.data.let {
                    result.data.let { ticket ->
                        binding.apply {
                            textViewCategory.text = ticket.category_name
                            //glide.load(ticket.image).into(imageViewTicket)
                            textViewDate.text = ticket.create_time
                            textViewNumber.text = ticket.tn
                            textViewStatus.text = ticket.current_event_label

                            when (ticket.current_event_label) {
                                AppConstants.StatusTickets.NEW -> {
                                    workerStatus.text =
                                        getString(R.string.your_specialist_is_not_assigned)
                                }
                                AppConstants.StatusTickets.ACCEPT -> {
                                    workerStatus.text =
                                        getString(R.string.your_specialist_on_his_way)
                                    assignedLabel.makeVisible()
                                    viewModel.getWorkerDetail(ticket.user_id)
                                }
                                AppConstants.StatusTickets.ARRIVED -> {
                                    workerStatus.text =
                                        getString(R.string.your_specialist_has_arrived)
                                    scanButton.makeVisible()
                                    assignedLabel.makeVisible()
                                    viewModel.getWorkerDetail(ticket.user_id)
                                }
                                AppConstants.StatusTickets.COMPLETE -> {
                                    workerStatus.text =
                                        getString(R.string.your_request_is_completed)
                                    assignedLabel.makeVisible()
                                    viewModel.getWorkerDetail(ticket.user_id)
                                }
                                AppConstants.StatusTickets.REJECT -> {
                                    workerStatus.text =
                                        getString(R.string.your_request_was_rejected)
                                }
                            }
                        }
                    }
                }
            }
            is TicketDetailUIModel.WorkerInfo -> {
                result.data.let {
                    binding.apply {
                        workerCard.makeVisible()
                        glide.load(it.image).circleCrop().into(workerAvatar)
                        workerName.text = it.firstname + it.lastname
                        workerRating.rating = it.avg_rating?.toFloat() ?: 0f
//                    workerCall
                    }
                }
            }
            is TicketDetailUIModel.Error -> {
                handleErrorMessage(result.error)
            }
//            is TicketDetailUIModel.BookmarkStatus -> {
//                when (result.bookmark) {
//                    Bookmark.BOOKMARK ->
//                        if (result.status) {
//                            showSnackBar(binding.rootView, getString(R.string.bookmark_success))
//                        } else {
//                            handleErrorMessage(getString(R.string.bookmark_error))
//                        }
//                    Bookmark.UN_BOOKMARK ->
//                        if (result.status) {
//                            showSnackBar(
//                                binding.rootView,
//                                getString(R.string.un_bookmark_success)
//                            )
//                        } else {
//                            handleErrorMessage(getString(R.string.bookmark_error))
//                        }
//                }
//            }
        }
    }
}