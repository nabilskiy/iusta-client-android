package com.ls.iusta.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentChangepassBinding
import com.ls.iusta.domain.models.settings.SettingUiModel
import com.ls.iusta.domain.models.user.ChangePassUiModel
import com.ls.iusta.extension.observe
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
                viewModel.changePass(
                    oldPassTextInputEditText.text.toString(),
                    newPassTextInputEditText.text.toString(),
                    newPassConfirmTextInputEditText.text.toString()
                )
            }
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

            }
            is ChangePassUiModel.Error -> {
                handleErrorMessage(result.error)
            }
        }
    }
}