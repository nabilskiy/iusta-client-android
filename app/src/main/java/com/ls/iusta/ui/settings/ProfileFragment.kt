package com.ls.iusta.ui.settings

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.google.android.gms.common.config.GservicesValue.value
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentProfileBinding
import com.ls.iusta.domain.models.auth.RegUiModel
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.user.User
import com.ls.iusta.domain.models.user.UserUiModel
import com.ls.iusta.extension.hideKeyboard
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

    private var results = emptyList<String?>()
    private var customers = emptyList<Customer>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var customerId: String

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
                    customerId
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

            schoolTextInputEditText.threshold = 2
            schoolTextInputEditText.addTextChangedListener(object : TextWatcher {
                private var timer = Timer()
                private val DELAY: Long = 1000L
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(query: Editable?) {
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            if (query.toString().length > 1)
                                viewModel.searchCustomer(query.toString())
                        }
                    }, DELAY)
                }
            })

            schoolTextInputEditText.setOnItemClickListener { adapterView, view, i, l ->
                (requireActivity() as MainActivity).hideKeyboard()
                customerId = customers[i].id.toString()
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
        customerId = user.customer_id.toString()
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
              //  handleLoading(false)
                setLocaleBtn(result.lang)
            }
            is UserUiModel.Updated ->{
                handleLoading(false)
            }
            is UserUiModel.SuccessSearch -> {
                //  handleLoading(false)
                if (result.data.success && result.data.response != null) {
                    customers = result.data.response!!
                    results = customers.map { it.name }
                    adapter = ArrayAdapter<String>(
                        requireContext(),
                        R.layout.item_search, results
                    )
                    binding.schoolTextInputEditText.setAdapter(adapter)
                    adapter.notifyDataSetChanged()
                    binding.schoolTextInputEditText.showDropDown()
                } else if (result.data.success && result.data.response == null) {
                   // handleErrorMessage(getString(R.string.nothing))
                } else {
                    result.data.message?.map {
                        handleErrorMessage(it.value[0])
                    }
                }
            }
        }
    }
}