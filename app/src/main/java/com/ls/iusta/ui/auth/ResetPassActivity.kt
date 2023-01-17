package com.ls.iusta.ui.auth

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
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
                viewModel.resetPass(emailTextInputEditText.text.toString())
            }

            backTv.setOnClickListener {
                finish()
            }

            emailTextInputEditText.doOnTextChanged { _, _, _, _ ->
                if (emailTextInputLayout.error != null) {
                    emailTextInputLayout.error = null
                }
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
                if (!event.result)
                    handleErrorMessage(getString(R.string.reset_email))
            }
            is ResetPassUiModel.Error -> {
                handleErrorMessage(event.error)
            }
            is ResetPassUiModel.EmptyEmail -> {
                binding.emailTextInputLayout.error = getString(R.string.empty_email)
            }
            is ResetPassUiModel.IncorrectEmail -> {
                binding.emailTextInputLayout.error = getString(R.string.incorrect_email)
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