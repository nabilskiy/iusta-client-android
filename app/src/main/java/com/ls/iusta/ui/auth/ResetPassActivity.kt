package com.ls.iusta.ui.auth

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import com.ls.iusta.base.BaseActivity
import com.ls.iusta.databinding.ActivityResetpassBinding
import com.ls.iusta.domain.models.auth.LoginUiModel
import com.ls.iusta.extension.observe
import com.ls.iusta.extension.startWithAnimation
import com.ls.iusta.presentation.viewmodel.user.LoginViewModel
import com.ls.iusta.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPassActivity : BaseActivity<ActivityResetpassBinding>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun getViewBinding() = ActivityResetpassBinding.inflate(layoutInflater)

    override fun initUI() {
        with(binding) {

            nextButton.setOnClickListener {

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
                MainActivity.startActivity(this@ResetPassActivity)
            }
            is LoginUiModel.Error -> {
                handleErrorMessage(event.error)
            }
        }
    }

    override fun initViewModel() {
        observe(viewModel.loginData, ::onLogin)
    }

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, ResetPassActivity::class.java)
            activity?.startWithAnimation(intent, false)
        }
    }
}