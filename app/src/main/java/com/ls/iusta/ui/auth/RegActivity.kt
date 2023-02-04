package com.ls.iusta.ui.auth

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.common.config.GservicesValue.value
import com.ls.iusta.R
import com.ls.iusta.base.BaseActivity
import com.ls.iusta.core.dialog.showDialog
import com.ls.iusta.databinding.ActivityRegisterBinding
import com.ls.iusta.domain.models.auth.RegUiModel
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.user.UserUiModel
import com.ls.iusta.extension.*
import com.ls.iusta.presentation.viewmodel.user.RegisterViewModel
import com.ls.iusta.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RegActivity : BaseActivity<ActivityRegisterBinding>() {

    private var results = emptyList<String?>()
    private var customers = emptyList<Customer>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var customerId: String
    private var customerIdIsSet: Boolean = false
    private var langId: String = "en"
    private lateinit var policyLink: String
    private lateinit var termsLink: String

    override val viewModel: RegisterViewModel by viewModels()

    override fun getViewBinding() = ActivityRegisterBinding.inflate(layoutInflater)

    override fun initViewModel() {
        observe(viewModel.regData, ::onReg)
        viewModel.getTerms()
    }

    override fun initUI() {
        //  setHints()
        //todo fix hints  implementation 'com.google.android.material:material:1.7.0'
        with(binding) {
            nextButton.setOnClickListener {
                clearErrors()
                val password = passwordTextInputEditText.text.toString()
                if (password.isEmpty())
                    passwordTextInputLayout.error = getString(R.string.empty_password)
                val passwordConfirm = confirmPassTextInputEditText.text.toString()
                if (passwordConfirm.isEmpty())
                    confirmPassTextInputLayout.error = getString(R.string.empty_password_confirm)
                val name = nameTextInputEditText.text.toString()
                if (name.isEmpty())
                    nameTextInputLayout.error = getString(R.string.empty_name)
                val surname = surnameTextInputEditText.text.toString()
                if (surname.isEmpty())
                    surnameTextInputLayout.error = getString(R.string.empty_surname)
                val phone = phoneTextInputEditText.text.toString()
                if (phone.isEmpty())
                    phoneTextInputLayout.error = getString(R.string.empty_phone)
                val email = emailTextInputEditText.text.toString()
                if (email.isEmpty())
                    emailTextInputLayout.error = getString(R.string.empty_email)
                if (!::customerId.isInitialized || customerId.isEmpty())
                    schoolTextInputLayout.error = getString(R.string.empty_school)

                if (privacy.isChecked && terms.isChecked) {
                    if (password.isNotEmpty() && passwordConfirm.isNotEmpty()
                        && name.isNotEmpty() && surname.isNotEmpty()
                        && phone.isNotEmpty() && email.isNotEmpty()
                    )
                        viewModel.startReg(
                            "",
                            password,
                            passwordConfirm,
                            name,
                            surname,
                            "",
                            phone,
                            "",
                            email,
                            customerId,
                            langId
                        )
                } else {
                    showDialog(
                        getString(R.string.app_name),
                        getString(R.string.privacy_and_terms),
                        "OK",
                        null
                    )
                    nested.smoothScrollTo(0, nested.getChildAt(0).height)
                }
            }

            login.setOnClickListener {
                LoginActivity.startActivity(this@RegActivity)
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
                            if (query.toString().length > 1
                            //&& !customerIdIsSet
                            )
                                viewModel.searchCustomer(query.toString())
                        }
                    }, DELAY)
                }
            })

            schoolTextInputEditText.setOnItemClickListener { adapterView, view, i, l ->
                hideKeyboard()
                // customerIdIsSet = true
                customerId = customers[i].id.toString()
            }

            messageDialog.setOnClickListener {
                messageDialog.apply {
                    fadeOut()
                    onBackPressed()
                }
            }

            privacyTxt.setOnClickListener {
                    openBrowser(policyLink)
            }
            termsTxt.setOnClickListener {
                    openBrowser(termsLink)
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


    private fun clearErrors() {
        with(binding) {
            passwordTextInputLayout.error = null
            confirmPassTextInputLayout.error = null
            nameTextInputLayout.error = null
            surnameTextInputLayout.error = null
            phoneTextInputLayout.error = null
            emailTextInputLayout.error = null
            schoolTextInputLayout.error = null
        }
    }

    private fun setHints() {
        with(binding) {
            passwordTextInputLayout.hint = getString(R.string.password)
            confirmPassTextInputLayout.hint = getString(R.string.password_confirm)
            nameTextInputLayout.hint = getString(R.string.name)
            surnameTextInputLayout.hint = getString(R.string.surname)
            phoneTextInputLayout.hint = getString(R.string.phone)
            emailTextInputLayout.hint = getString(R.string.emailaddress)
            schoolTextInputEditText.hint = getString(R.string.school)
        }
    }

    private fun setLocaleBtn(lang: String) {
        langId = lang
        with(binding) {
            when (lang) {
                "az" -> {
                    azButton.background =
                        AppCompatResources.getDrawable(
                            this@RegActivity,
                            R.drawable.btn_accent_default
                        )
                    engButton.background = AppCompatResources.getDrawable(
                        this@RegActivity,
                        R.drawable.btn_lighter_grey_default
                    )
                    ruButton.background = AppCompatResources.getDrawable(
                        this@RegActivity,
                        R.drawable.btn_lighter_grey_default
                    )
                }
                "en" -> {
                    azButton.background = AppCompatResources.getDrawable(
                        this@RegActivity,
                        R.drawable.btn_lighter_grey_default
                    )
                    engButton.background =
                        AppCompatResources.getDrawable(
                            this@RegActivity,
                            R.drawable.btn_accent_default
                        )
                    ruButton.background = AppCompatResources.getDrawable(
                        this@RegActivity,
                        R.drawable.btn_lighter_grey_default
                    )
                }
                "ru" -> {
                    azButton.background = AppCompatResources.getDrawable(
                        this@RegActivity,
                        R.drawable.btn_lighter_grey_default
                    )
                    engButton.background = AppCompatResources.getDrawable(
                        this@RegActivity,
                        R.drawable.btn_lighter_grey_default
                    )
                    ruButton.background =
                        AppCompatResources.getDrawable(
                            this@RegActivity,
                            R.drawable.btn_accent_default
                        )
                }
            }
        }
    }


    private fun onReg(event: RegUiModel) {
        if (event.isRedelivered) return
        when (event) {
            is RegUiModel.Loading -> {
                handleLoading(true)
            }
            is RegUiModel.Success -> {
                handleLoading(false)
                if (event.result.success == true) {
                    binding.messageDialog.makeVisible()
                } else {
                    event.result.message?.map {
                        handleErrorMessage(it.value.get(0))
                    }
                }
            }
            is RegUiModel.SuccessSearch -> {
                //  handleLoading(false)
                if (event.data.success && event.data.response != null) {
                    customers = event.data.response!!
                    results = customers.map { it.name }
                    adapter = ArrayAdapter<String>(
                        this@RegActivity,
                        R.layout.item_search, results
                    )
                    binding.schoolTextInputEditText.setAdapter(adapter)
                    adapter.notifyDataSetChanged()
                    binding.schoolTextInputEditText.showDropDown()
                } else if (event.data.success && event.data.response == null) {
                    handleErrorMessage(getString(R.string.nothing))
                } else {
                    event.data.message?.map {
                        handleErrorMessage(it.value[0])
                    }
                }
            }
            is RegUiModel.Error -> {
                handleErrorMessage(event.error)
            }
            is RegUiModel.Docs -> {
                event.data.map {
                    when (it.name) {
                        "PrivacyPolicy" -> {
                            policyLink = it.value.toString()
                        }
                        "UserTermsOfService" -> {
                            termsLink = it.value.toString()
                        }
                    }
                }
            }
            is RegUiModel.ChangeLocale -> {
                handleLoading(false)
                updateLocale(Locale(event.lang))
            }
            is RegUiModel.GetLocale -> {
                handleLoading(false)
                setLocaleBtn(event.lang)
            }
        }
    }

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, RegActivity::class.java)
            activity?.startWithAnimation(intent, false)
        }
    }
}