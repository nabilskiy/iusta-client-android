package com.ls.iusta.ui.settings.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentAboutBinding
import com.ls.iusta.databinding.FragmentTermsBinding
import com.ls.iusta.domain.models.info.FaqUiModel
import com.ls.iusta.domain.models.info.TermsUiModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.info.FaqViewModel
import com.ls.iusta.presentation.viewmodel.info.TermsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TermsFragment : BaseFragment<FragmentTermsBinding, TermsViewModel>() {

    override fun getViewBinding(): FragmentTermsBinding =
        FragmentTermsBinding.inflate(layoutInflater)

    override val viewModel: TermsViewModel by viewModels()

    private lateinit var typeUrl: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        typeUrl = arguments?.getString("url_type").toString()
        observe(viewModel.terms, ::onViewStateChange)
        viewModel.getTerms()
    }


    private fun onViewStateChange(result: TermsUiModel) {
        if (result.isRedelivered) return
        when (result) {
            is TermsUiModel.Loading -> {
                handleLoading(true)
            }
            is TermsUiModel.Success -> {
                handleLoading(false)
                result.data.map {
                    if (typeUrl.equals(it.name))
                        binding.webview.loadUrl(it.value.toString())
                }
            }
            is TermsUiModel.Error -> {
                handleErrorMessage(result.error)
            }
        }
    }
}