package com.ls.iusta.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.gms.common.config.GservicesValue.value
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentChangepassBinding
import com.ls.iusta.domain.models.settings.SettingUiModel
import com.ls.iusta.domain.models.user.ChangePassUiModel
import com.ls.iusta.extension.observe
import com.ls.iusta.extension.showSnackBar
import com.ls.iusta.presentation.viewmodel.user.ChangePassViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePassFragment : BaseFragment<FragmentChangepassBinding, ChangePassViewModel>() {

    override fun getViewBinding(): FragmentChangepassBinding =
        FragmentChangepassBinding.inflate(layoutInflater)

    override val viewModel: ChangePassViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.settings, ::onViewStateChange)
        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            nextButton.setOnClickListener {
                clearErrors()
                val oldpass = oldPassTextInputEditText.text.toString()
                val newpass = newPassTextInputEditText.text.toString()
                val newpassconfirm = newPassConfirmTextInputEditText.text.toString()
                if (oldpass.isEmpty())
                    oldPassTextInputLayout.error = getString(R.string.empty_field)
                if (newpass.isEmpty())
                    newPassTextInputLayout.error = getString(R.string.empty_field)
                if (newpassconfirm.isEmpty())
                    newPassConfirmTextInputLayout.error = getString(R.string.empty_field)
                if (oldpass.isNotEmpty() && newpass.isNotEmpty() && newpassconfirm.isNotEmpty())
                    viewModel.changePass(
                        oldpass, newpass, newpassconfirm
                    )
                else handleErrorMessage(getString(R.string.empty_fields_error))
            }
        }
    }

    private fun clearErrors() {
        with(binding) {
            oldPassTextInputLayout.error = null
            newPassTextInputLayout.error = null
            newPassConfirmTextInputLayout.error = null
        }
    }

    private fun onViewStateChange(result: ChangePassUiModel) {
        if (result.isRedelivered) return
        when (result) {
            is ChangePassUiModel.Loading -> {
                handleLoading(true)
            }
            is ChangePassUiModel.Success -> {
                handleLoading(false)
                if (result.result.success == true)
                    showSnackBar(binding.root, getString(R.string.fragment_changepass_success))
                else
                    result.result.message?.map {
                        handleErrorMessage(it.value.get(0))
                    }
            }
            is ChangePassUiModel.Error -> {
                handleErrorMessage(result.error)
                showSnackBar(binding.root, getString(R.string.fragment_changepass_failure))
            }
        }
    }
}