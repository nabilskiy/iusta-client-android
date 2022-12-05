package com.ls.iusta.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.core.theme.ThemeUtils
import com.ls.iusta.databinding.FragmentAboutBinding
import com.ls.iusta.databinding.FragmentChangepassBinding
import com.ls.iusta.databinding.FragmentSettingsBinding
import com.ls.iusta.domain.models.settings.SettingUiModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.AboutViewModel
import com.ls.iusta.presentation.viewmodel.ChangePassViewModel
import com.ls.iusta.presentation.viewmodel.SettingsViewModel
import com.ls.iusta.ui.ticketslist.TicketsListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChangePassFragment : BaseFragment<FragmentChangepassBinding, ChangePassViewModel>() {

    override fun getViewBinding(): FragmentChangepassBinding =
        FragmentChangepassBinding.inflate(layoutInflater)

    override val viewModel: ChangePassViewModel by viewModels()

    @Inject
    lateinit var settingAdapter: SettingsAdapter

    @Inject
    lateinit var themeUtils: ThemeUtils

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
            is SettingUiModel.NightMode -> {
                themeUtils.setNightMode(result.nightMode)
            }
        }
    }
}