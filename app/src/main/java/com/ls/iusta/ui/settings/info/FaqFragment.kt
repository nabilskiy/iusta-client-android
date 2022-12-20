package com.ls.iusta.ui.settings.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentAboutBinding
import com.ls.iusta.domain.models.info.FaqUiModel
import com.ls.iusta.extension.observe
import com.ls.iusta.presentation.viewmodel.info.FaqViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FaqFragment : BaseFragment<FragmentAboutBinding, FaqViewModel>() {

    override fun getViewBinding(): FragmentAboutBinding =
        FragmentAboutBinding.inflate(layoutInflater)

    override val viewModel: FaqViewModel by viewModels()

    @Inject
    lateinit var faqAdapter: AboutAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.faq, ::onViewStateChange)
        setupRecyclerView()
        viewModel.getFaq()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSettings.apply {
            adapter = faqAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun onViewStateChange(result: FaqUiModel) {
        if (result.isRedelivered) return
        when (result) {
            is FaqUiModel.Loading -> {
                handleLoading(true)
            }
            is FaqUiModel.Success -> {
                handleLoading(false)
                faqAdapter.list = result.data
            }
            is FaqUiModel.Error -> {
                handleErrorMessage(result.error)
            }
        }
    }
}