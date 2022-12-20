package com.ls.iusta.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.ls.iusta.R
import com.ls.iusta.core.dialog.dismissLoadingDialog
import com.ls.iusta.core.dialog.showLoadingDialog
import com.ls.iusta.extension.showSnackBar
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import timber.log.Timber

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

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

//    override fun attachBaseContext(newBase: Context) {
//        oldPrefLocaleCode = Storage(newBase).getPreferredLocale()
//        applyOverrideConfiguration(LocaleUtil.getLocalizedConfiguration(oldPrefLocaleCode))
//        super.attachBaseContext(newBase)
//    }

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