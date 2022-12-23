package com.ls.iusta.ui.ticketdetail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.ls.iusta.extension.makeGone
import com.ls.iusta.extension.makeVisible
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.managers.PermissionManager
import com.ls.iusta.presentation.utils.AppConstants
import com.ls.iusta.presentation.viewmodel.tickets.TicketDetailViewModel
import com.ls.iusta.ui.qrscanner.ScannerActivity
import com.ls.iusta.ui.rating.AddRatingActivity
import dagger.hilt.android.AndroidEntryPoint
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

    private var ticketId: Long = 0
    private var workerId: Int = 0
    private lateinit var workerPhone: String
    private var ratingSet: Boolean = false
    private lateinit var ticketStatus: String

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                checkWorkersTicket(intent?.getStringExtra("ticket_id")?.toLong() ?: 0)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.ticketDetail, ::onViewStateChange)
        viewModel.getTicketDetail(args.ticketId)
        initUi()
    }

    private fun initUi() {
        binding.apply {
            refresh.setOnRefreshListener {
                binding.refresh.isRefreshing = false
                viewModel.getTicketDetail(args.ticketId)
            }
            scanButton.setOnClickListener {
                requestPermissions()
            }
            nextButton.setOnClickListener {
                viewModel.sendNoteForTicket(ticketId, "Agent arrived")
            }
            cancelButton.setOnClickListener {
                scanResultsDialog.makeGone()
            }
            workerCallOrRate.setOnClickListener {
                if (!ticketStatus.equals(AppConstants.StatusTickets.COMPLETE)) {
                    val callIntent: Intent = Uri.parse("tel:" + workerPhone).let { number ->
                        Intent(Intent.ACTION_DIAL, number)
                    }
                    startActivity(callIntent)
                } else if (!ratingSet && ticketStatus.equals(AppConstants.StatusTickets.COMPLETE)) {
                    AddRatingActivity.startActivity(requireActivity(), ticketId, workerId)
                }
            }
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

    private fun checkWorkersTicket(scannedTicketId: Long) {
        showScanningResult(ticketId == scannedTicketId)
    }

    private fun showScanningResult(match: Boolean) {
        binding.apply {
            scanResultsDialog.makeVisible()
            if (match) {
                txTv.text = getString(R.string.assistant_identified)
                icon.setImageResource(R.drawable.ic_done)
                cancelButton.makeGone()
            } else {
                txTv.text = getString(R.string.assistant_not_identified)
                nextButton.text = getString(R.string.proceed_anyway)
                icon.setImageResource(R.drawable.ic_warning)
                cancelButton.makeVisible()
            }
        }
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
                                // glide.load(ticket.image).circleCrop().into(imageViewTicket)
                            textViewDate.text = ticket.create_time
                            textViewNumber.text = ticket.tn
                            textViewStatus.text = ticket.current_event_label
                            ticketId = ticket.id
                            workerId = ticket.user_id
                            ticketStatus = ticket.current_event_label
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
                                    workerCallOrRate.setImageResource(R.drawable.ic_rate)
                                    assignedLabel.makeVisible()
                                    viewModel.getWorkerDetail(ticket.user_id)
                                    viewModel.checkRating(ticket.id)
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
                        workerPhone = it.phone_number.toString()
                        glide.load(it.image).circleCrop().into(workerAvatar)
                        workerName.text = it.firstname + " " + it.lastname
                        workerRating.rating = it.avg_rating?.toFloat() ?: 0f
                    }
                }
            }
            is TicketDetailUIModel.AddNote -> {
                handleLoading(false)
                binding.scanResultsDialog.makeGone()
            }
            is TicketDetailUIModel.GetRating -> {
                Log.d("TAG", "onViewStateChange: ")
                if (!result.data.rating.equals("not_found")) {
                    ratingSet = true
                    binding.workerCallOrRate.makeGone()
                }
            }
            is TicketDetailUIModel.Error -> {
                handleErrorMessage(result.error)
            }
        }
    }
}