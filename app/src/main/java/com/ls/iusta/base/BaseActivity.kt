package com.ls.iusta.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ls.iusta.IUSTAApp
import com.ls.iusta.R
import com.ls.iusta.core.dialog.dismissLoadingDialog
import com.ls.iusta.core.dialog.showLoadingDialog
import com.ls.iusta.extension.showSnackBar
import com.ls.iusta.extension.startWithAnimation
import com.ls.iusta.presentation.utils.PresentationPreferencesHelper
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import com.ls.iusta.ui.MainActivity
import com.zeugmasolutions.localehelper.LocaleAwareCompatActivity
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity<B : ViewBinding> : LocaleAwareCompatActivity() {

    protected lateinit var binding: B

    protected abstract fun getViewBinding(): B

    protected abstract val viewModel: BaseViewModel

    protected abstract fun initUI()

    protected abstract fun initViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        initUI()
        initViewModel()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.anim.close_fade_out)
    }

    protected open fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else dismissLoadingDialog()
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        dismissLoadingDialog()
        Timber.e(message)
        showSnackBar(binding.root, message)
    }


}