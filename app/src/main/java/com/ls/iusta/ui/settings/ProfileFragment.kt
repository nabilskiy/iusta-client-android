package com.ls.iusta.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.core.theme.ThemeUtils
import com.ls.iusta.databinding.FragmentProfileBinding
import com.ls.iusta.databinding.FragmentSettingsBinding
import com.ls.iusta.domain.models.settings.SettingUiModel
import com.ls.iusta.domain.models.user.User
import com.ls.iusta.domain.models.user.UserUiModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.ProfileViewModel
import com.ls.iusta.presentation.viewmodel.SettingsViewModel
import com.ls.iusta.ui.ticketslist.TicketsListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    override val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.userInfo, ::onUserLoaded)
        setupViews()
        viewModel.getUser()
    }

    private fun setupViews() {

    }

    private fun bindData(user: User){
        binding.schoolTextInputEditText.setText(user.customer_name)
        binding.nameTextInputEditText.setText(user.firstname)
        binding.surnameTextInputEditText.setText(user.lastname)
        binding.emailTextInputEditText.setText(user.email)
        binding.phoneTextInputEditText.setText(user.phone_number)
        binding.birthdayTextInputEditText.setText(user.birthday)
    }

    private fun onUserLoaded(result: UserUiModel) {
        if (result.isRedelivered) return
        when (result) {
            is UserUiModel.Loading -> {
                handleLoading(true)
            }
            is UserUiModel.Success -> {
                handleLoading(false)
                bindData(result.data)
            }
            is UserUiModel.Error -> {
                handleErrorMessage(result.error)
            }
        }
    }
}