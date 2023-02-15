package com.ls.iusta.ui.auth

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
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
                if (passwordTextInputEditText.isPassword()) {
                    passwordTextInputEditText.setPasswordState(false)
                    passwordButton.setImageResource(R.drawable.ic_password_on)
                } else {
                    passwordTextInputEditText.setPasswordState(true)
                    passwordButton.setImageResource(R.drawable.ic_password_off)
                }
                passwordTextInputEditText.setSelection(passwordTextInputEditText.text.toString().length)
            }

            nextButton.setOnClickListener {
                binding.passwordTextInputLayout.error = null
                val email = emailTextInputEditText.text.toString()
                val password = passwordTextInputEditText.text.toString()
                if (password.length > 7)
                    viewModel.startLogin(email, password)
                else
                    binding.passwordTextInputLayout.error = getString(R.string.short_password)
            }

            signUp.setOnClickListener {
                RegActivity.startActivity(this@LoginActivity)
            }

            forgotPass.setOnClickListener {
                ResetPassActivity.startActivity(this@LoginActivity)
            }
            emailTextInputEditText.doOnTextChanged { _, _, _, _ ->
                if (emailTextInputLayout.error != null) {
                    emailTextInputLayout.error = null
                }
            }

            passwordTextInputEditText.doOnTextChanged { _, _, _, _ ->
                if (passwordTextInputLayout.error != null) {
                    passwordTextInputLayout.error = null
                }
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
                if (event.data.success) {
                    MainActivity.startActivity(this@LoginActivity)
                } else {
                    handleErrorMessage(event.data.message)
                }
            }
            is LoginUiModel.Error -> {
                handleErrorMessage(event.error)
            }
            is LoginUiModel.EmptyEmail -> {
                binding.emailTextInputLayout.error = getString(R.string.empty_email)
            }
            is LoginUiModel.IncorrectEmail -> {
                binding.emailTextInputLayout.error = getString(R.string.incorrect_email)
            }
            is LoginUiModel.PasswordError -> {
                binding.passwordTextInputLayout.error = getString(R.string.empty_password)
            }

        }
    }

    override fun initViewModel() {
        observe(viewModel.loginData, ::onLogin)
    }

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startWithAnimation(intent, true)
        }
    }
}