package com.ls.iusta.ui.auth

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import com.ls.iusta.R
import com.ls.iusta.base.BaseActivity
import com.ls.iusta.databinding.ActivityLoginBinding
import com.ls.iusta.domain.models.auth.LoginUiModel
import com.ls.iusta.extension.isPassword
import com.ls.iusta.extension.observe
import com.ls.iusta.extension.setPasswordState
import com.ls.iusta.extension.startWithAnimation
import com.ls.iusta.presentation.viewmodel.user.LoginViewModel
import com.ls.iusta.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

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

                if(email.isEmpty())
                    binding.emailTextInputLayout.error = "Empty email"

                viewModel.startLogin(email, password)
            }

            signUp.setOnClickListener {
                RegActivity.startActivity(this@LoginActivity)
            }

            forgotPass.setOnClickListener {
                ResetPassActivity.startActivity(this@LoginActivity)
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
                if (event.data.success){
                    MainActivity.startActivity(this@LoginActivity)
                }else{
                    handleErrorMessage(event.data.message)
                }
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
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startWithAnimation(intent, false)
        }
    }
}