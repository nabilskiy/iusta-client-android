package com.ls.iusta.ui.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentProfileBinding
import com.ls.iusta.domain.models.user.User
import com.ls.iusta.domain.models.user.UserUiModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.user.ProfileViewModel
import com.ls.iusta.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    override val viewModel: ProfileViewModel by viewModels()

    private var customerId: Int? = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.userInfo, ::onUserLoaded)
        setupViews()
        viewModel.getUser()
    }

    private fun setupViews() {
        binding.apply {
            nextButton.setOnClickListener {
                viewModel.updateUser(
                    nameTextInputEditText.text.toString(),
                    surnameTextInputEditText.text.toString(),
                    "",
                    phoneTextInputEditText.text.toString(),
                    birthdayTextInputEditText.text.toString(),
                    emailTextInputEditText.text.toString(),
                    customerId.toString()
                )
            }
            azButton.setOnClickListener {
                setLocaleBtn("az")
                viewModel.setLocale("az")
            }
            engButton.setOnClickListener {
                setLocaleBtn("en")
                viewModel.setLocale("en")
            }
            ruButton.setOnClickListener {
                setLocaleBtn("ru")
                viewModel.setLocale("ru")
            }
        }
    }

    private fun setLocaleBtn(lang: String) {
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
    }

    private fun bindData(user: User) {
        customerId = user.customer_id
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
                handleLoading(false)
                (requireActivity() as MainActivity).updateLocale(Locale(result.lang))
            }
            is UserUiModel.GetLocale -> {
                handleLoading(false)
                setLocaleBtn(result.lang)
            }
            is UserUiModel.Updated ->{
                handleLoading(false)

            }
        }
    }
}