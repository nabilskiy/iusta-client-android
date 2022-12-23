package com.ls.iusta.ui.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.core.locale.LocaleUtils
import com.ls.iusta.core.theme.ThemeUtils
import com.ls.iusta.databinding.FragmentProfileBinding
import com.ls.iusta.domain.models.user.User
import com.ls.iusta.domain.models.user.UserUiModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.user.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<com.ls.iusta.databinding.FragmentProfileBinding, ProfileViewModel>() {

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    override val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var localeUtils: LocaleUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.userInfo, ::onUserLoaded)
        setupViews()
        viewModel.getUser()
    }

    private fun setupViews() {
        binding.apply {
            nextButton.setOnClickListener {
                viewModel.updateUser(nameTextInputEditText.text.toString(),
                    surnameTextInputEditText.text.toString(),
                "", phoneTextInputEditText.text.toString(),
                birthdayTextInputEditText.text.toString(), emailTextInputEditText.text.toString(),
                "1081")
            }
            azButton.setOnClickListener {
                changeLocale("az")
            }
            engButton.setOnClickListener {
                changeLocale("en")
            }
            ruButton.setOnClickListener {
                changeLocale("ru")
            }
        }
    }

    private fun changeLocale(lang: String) {
        when (lang) {
            "az" -> {
                binding.azButton.background =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.btn_accent_default)
                binding.engButton.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.btn_lighter_grey_default
                )
                binding.ruButton.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.btn_lighter_grey_default
                )
            }
            "en" -> {
                binding.azButton.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.btn_lighter_grey_default
                )
                binding.engButton.background =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.btn_accent_default)
                binding.ruButton.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.btn_lighter_grey_default
                )
            }
            "ru" -> {
                binding.azButton.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.btn_lighter_grey_default
                )
                binding.engButton.background = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.btn_lighter_grey_default
                )
                binding.ruButton.background =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.btn_accent_default)
            }
        }
        viewModel.setLocale(lang)
    }

    private fun bindData(user: User) {
        binding.apply {
            schoolTextInputEditText.setText(user.customer_name)
            nameTextInputEditText.setText(user.firstname)
            surnameTextInputEditText.setText(user.lastname)
            emailTextInputEditText.setText(user.email)
            phoneTextInputEditText.setText(user.phone_number)
            birthdayTextInputEditText.setText(user.birthday)
        }

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
            is UserUiModel.ChangeLocale -> {
                localeUtils.setLocale(requireContext(), result.lang)
            }
            is UserUiModel.GetLocale -> {
                handleLoading(false)
                changeLocale(result.lang)
            }
        }
    }
}