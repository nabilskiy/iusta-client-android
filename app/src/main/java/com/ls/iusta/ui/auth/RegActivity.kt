package com.ls.iusta.ui.auth

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.google.android.gms.common.config.GservicesValue.value
import com.ls.iusta.R
import com.ls.iusta.base.BaseActivity
import com.ls.iusta.databinding.ActivityRegisterBinding
import com.ls.iusta.domain.models.auth.RegUiModel
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.extension.*
import com.ls.iusta.presentation.viewmodel.user.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegActivity : BaseActivity<ActivityRegisterBinding>() {

    private var results = emptyList<String?>()
    private var customers = emptyList<Customer>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var customerId: String
    private lateinit var policyLink: String
    private lateinit var termsLink: String

    override val viewModel: RegisterViewModel by viewModels()

    override fun getViewBinding() = ActivityRegisterBinding.inflate(layoutInflater)

    override fun initViewModel() {
        observe(viewModel.regData, ::onReg)
        viewModel.getTerms()
    }

    override fun initUI() {
        with(binding) {
            nextButton.setOnClickListener {
                viewModel.startReg(
                    "",
                    passwordTextInputEditText.text.toString(),
                    confirmPassTextInputEditText.text.toString(),
                    nameTextInputEditText.text.toString(),
                    surnameTextInputEditText.text.toString(),
                    "",
                    phoneTextInputEditText.text.toString(),
                    "",
                    emailTextInputEditText.text.toString(),
                    customerId,
                    "az"
                )
            }

            login.setOnClickListener {
                LoginActivity.startActivity(this@RegActivity)
            }

            schoolTextInputEditText.threshold = 2
            schoolTextInputEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(query: Editable?) {
                    if (query.toString().length > 1)
                        viewModel.searchCustomer(query.toString())
                }
            })

            schoolTextInputEditText.setOnItemClickListener { adapterView, view, i, l ->
                customerId = customers[i].id.toString()
            }

            messageDialog.setOnClickListener {
                messageDialog.apply {
                    fadeOut()
                    onBackPressed()
                }
            }

            privacy.setOnClickListener {
                if (privacy.isChecked)
                    openBrowser(policyLink)
            }
            terms.setOnClickListener {
                if (terms.isChecked)
                    openBrowser(termsLink)
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
                if (event.result) {
                    binding.messageDialog.makeVisible()
                }
            }
            is RegUiModel.SuccessSearch -> {
                handleLoading(false)
                customers = event.data
                results = event.data.map { it.name }
                adapter = ArrayAdapter<String>(
                    this@RegActivity,
                    R.layout.item_search, results
                )
                binding.schoolTextInputEditText.setAdapter(adapter)
                adapter.notifyDataSetChanged()
                binding.schoolTextInputEditText.showDropDown()
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
        }
    }

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, RegActivity::class.java)
            activity?.startWithAnimation(intent, false)
        }
    }
}