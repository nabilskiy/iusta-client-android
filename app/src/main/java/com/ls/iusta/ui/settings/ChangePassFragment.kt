package com.ls.iusta.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentChangepassBinding
import com.ls.iusta.domain.models.settings.SettingUiModel
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

    }

    private fun onViewStateChange(result: SettingUiModel) {
        if (result.isRedelivered) return
        when (result) {
            is SettingUiModel.Loading -> {
                handleLoading(true)
            }
            is SettingUiModel.Success -> {
                handleLoading(false)

            }
            is SettingUiModel.Error -> {
                handleErrorMessage(result.error)
            }
            is SettingUiModel.NightMode -> {

            }
        }
    }
}