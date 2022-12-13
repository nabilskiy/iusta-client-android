package com.ls.iusta.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ls.iusta.base.BaseActivity
import com.ls.iusta.databinding.ActivityLoginBinding
import com.ls.iusta.databinding.ActivitySplashBinding
import com.ls.iusta.domain.models.auth.AuthUiModel
import com.ls.iusta.domain.models.auth.LoginUiModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.LoginViewModel
import com.ls.iusta.ui.auth.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override val viewModel: LoginViewModel by viewModels()

    override fun initUI() {
        viewModel.isLogged()
    }

    override fun initViewModel() {
        observe(viewModel.loginData, ::onAuth)
    }

    private fun onAuth(event: LoginUiModel) {
        if (event.isRedelivered) return
        when (event) {
            is LoginUiModel.Loading -> {
                handleLoading(true)
            }
            is LoginUiModel.Success -> {

            }
            is LoginUiModel.CheckLogin ->{
                handleLoading(false)
                lifecycleScope.launch {
                    delay(1000)
                    nextActivity(event.status)
                }
            }
            is LoginUiModel.Error -> {
                handleErrorMessage(event.error)
            }
        }
    }

    private fun nextActivity(isLogged: Boolean?) {
        if (isLogged == true)
            MainActivity.startActivity(this@SplashActivity)
        else
            LoginActivity.startActivity(this@SplashActivity)
        // close splash activity
        finish()
    }
}