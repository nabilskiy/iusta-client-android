package com.ls.iusta.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.core.theme.ThemeUtils
import com.ls.iusta.databinding.FragmentAboutBinding
import com.ls.iusta.domain.models.settings.SettingUiModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.info.AboutViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding, AboutViewModel>() {

    override fun getViewBinding(): FragmentAboutBinding =
        FragmentAboutBinding.inflate(layoutInflater)

    override val viewModel: AboutViewModel by viewModels()

    @Inject
    lateinit var settingAdapter: SettingsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.settings, ::onViewStateChange)
        setupRecyclerView()
        viewModel.getSettings()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSettings.apply {
            adapter = settingAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        settingAdapter.setItemClickListener { selectedSetting ->
            when (selectedSetting.id) {
                1 -> {
                    findNavController().navigate(
                        AboutFragmentDirections.actionAboutFragmentToFaqFragment()
                    )
                }
                2 -> {
                    findNavController().navigate(
                        AboutFragmentDirections.actionAboutFragmentToPrivacyFragment("PrivacyPolicy")
                    )
                }
                3 -> {
                    findNavController().navigate(
                        AboutFragmentDirections.actionAboutFragmentToTermsFragment("UserTermsOfService")
                    )
                }
            }
        }
    }

    private fun onViewStateChange(result: SettingUiModel) {
        if (result.isRedelivered) return
        when (result) {
            is SettingUiModel.Loading -> {
                handleLoading(true)
            }
            is SettingUiModel.Success -> {
                handleLoading(false)
                settingAdapter.list = result.data
            }
            is SettingUiModel.Error -> {
                handleErrorMessage(result.error)
            }
        }
    }
}