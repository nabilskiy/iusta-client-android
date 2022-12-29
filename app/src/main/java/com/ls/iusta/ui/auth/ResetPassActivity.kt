package com.ls.iusta.ui.auth

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import com.ls.iusta.R
import com.ls.iusta.base.BaseActivity
import com.ls.iusta.databinding.ActivityResetpassBinding
import com.ls.iusta.domain.models.auth.LoginUiModel
import com.ls.iusta.domain.models.auth.ResetPassUiModel
import com.ls.iusta.extension.observe
import com.ls.iusta.extension.showDialog
import com.ls.iusta.extension.startWithAnimation
import com.ls.iusta.presentation.viewmodel.user.LoginViewModel
import com.ls.iusta.presentation.viewmodel.user.ResetPassViewModel
import com.ls.iusta.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPassActivity : BaseActivity<ActivityResetpassBinding>() {

    override val viewModel: ResetPassViewModel by viewModels()

    override fun getViewBinding() = ActivityResetpassBinding.inflate(layoutInflater)

    override fun initUI() {
        with(binding) {
            nextButton.setOnClickListener {
                val email = emailTextInputEditText.text.toString()
                if (email.isNotEmpty())
                    viewModel.resetPass(email)
                else emailTextInputLayout.error = getString(R.string.resetpass_error_empty)
            }
        }
    }

    private fun onResetPass(event: ResetPassUiModel) {
        if (event.isRedelivered) return
        when (event) {
            is ResetPassUiModel.Loading -> {
                handleLoading(true)
            }
            is ResetPassUiModel.Success -> {
                handleLoading(false)
            }
            is ResetPassUiModel.Error -> {
                handleErrorMessage(event.error)
            }
        }
    }

    override fun initViewModel() {
        observe(viewModel.resetPassData, ::onResetPass)
    }

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, ResetPassActivity::class.java)
            activity?.startWithAnimation(intent, false)
        }
    }
}