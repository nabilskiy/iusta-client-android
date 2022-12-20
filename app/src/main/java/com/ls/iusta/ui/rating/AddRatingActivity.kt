package com.ls.iusta.ui.rating

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.RequestManager
import com.ls.iusta.base.BaseActivity
import com.ls.iusta.databinding.ActivityAddRatingBinding
import com.ls.iusta.domain.models.worker.AddRatingUiModel
import com.ls.iusta.extension.*
import com.ls.iusta.presentation.viewmodel.worker.AddRatingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddRatingActivity : BaseActivity<ActivityAddRatingBinding>() {

    override val viewModel: AddRatingViewModel by viewModels()

    override fun getViewBinding() = ActivityAddRatingBinding.inflate(layoutInflater)

    @Inject
    lateinit var glide: RequestManager

    private var ticketId: Long = 0
    private var workerId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workerId = intent.getIntExtra("id", 0)
        ticketId = intent.getLongExtra("ticket_id", 0)
        viewModel.getWorkerDetail(workerId)
    }

    override fun initUI() {
        with(binding) {
            nextButton.setOnClickListener {
                viewModel.addRating(workerRating.rating.toInt(),ticketId, note.text.toString())
            }
            close.setOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun initViewModel() {
        observe(viewModel.ratingData, ::onViewStateChange)
    }


    private fun onViewStateChange(result: AddRatingUiModel) {
        if (result.isRedelivered) return
        when (result) {
            is AddRatingUiModel.Loading -> {
                handleLoading(true)
            }
            is AddRatingUiModel.Success -> {
                handleLoading(false)
                showSnackBar(binding.root, result.result.toString())
                finish()
            }
            is AddRatingUiModel.WorkerInfo -> {
                result.data.let {
                    binding.apply {
                        glide.load(it.image).circleCrop().into(workerAvatar)
                        workerName.text = it.firstname +" "+ it.lastname
                        workerRating.rating = it.avg_rating?.toFloat() ?: 0f
                    }
                }
            }
            is AddRatingUiModel.Error -> {
                handleErrorMessage(result.error)
            }
        }
    }


    companion object {
        fun startActivity(activity: Activity?, ticketId:Long, workerId: Int) {
            val intent = Intent(activity, AddRatingActivity::class.java)
            intent.putExtra("ticket_id", ticketId)
            intent.putExtra("id", workerId)
            activity?.startWithAnimation(intent, false)
        }
    }
}