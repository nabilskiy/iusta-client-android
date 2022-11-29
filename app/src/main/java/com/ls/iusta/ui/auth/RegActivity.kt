package com.ls.iusta.ui.auth

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import com.ls.iusta.R
import com.ls.iusta.base.BaseActivity
import com.ls.iusta.databinding.ActivityLoginBinding
import com.ls.iusta.databinding.ActivityRegisterBinding
import com.ls.iusta.domain.models.auth.LoginUiModel
import com.ls.iusta.extension.isPassword
import com.ls.iusta.extension.observe
import com.ls.iusta.extension.setPasswordState
import com.ls.iusta.extension.startWithAnimation
import com.ls.iusta.presentation.viewmodel.LoginViewModel
import com.ls.iusta.presentation.viewmodel.RegisterViewModel
import com.ls.iusta.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegActivity : BaseActivity<ActivityRegisterBinding>() {

    override val viewModel: RegisterViewModel by viewModels()

    override fun getViewBinding() = ActivityRegisterBinding.inflate(layoutInflater)

    override fun initUI() {
        with(binding) {

            nextButton.setOnClickListener {

            }

            login.setOnClickListener {
                LoginActivity.startActivity(this@RegActivity)
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
                MainActivity.startActivity(this@RegActivity)
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
            val intent = Intent(activity, RegActivity::class.java)
            activity?.startWithAnimation(intent, true)
        }
    }
}