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
            passwordButton.setOnClickListener {
                if (passwordEditText.isPassword()) {
                    passwordEditText.setPasswordState(false)
                    passwordButton.setImageResource(R.drawable.ic_password_on)
                } else {
                    passwordEditText.setPasswordState(true)
                    passwordButton.setImageResource(R.drawable.ic_password_off)
                }
                passwordEditText.setSelection(passwordEditText.text.toString().length)
            }

            nextButton.setOnClickListener {
                val email = binding.emailTextInputEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                viewModel.startLogin(email, password)
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