package com.ls.iusta.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentContactusBinding
import com.ls.iusta.domain.models.info.AboutUiModel
import com.ls.iusta.extension.observe
import com.ls.iusta.extension.showSnackBar
import com.ls.iusta.presentation.viewmodel.info.ContactUsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ContactUsFragment : BaseFragment<FragmentContactusBinding, ContactUsViewModel>() {

    override fun getViewBinding(): FragmentContactusBinding =
        FragmentContactusBinding.inflate(layoutInflater)

    override val viewModel: ContactUsViewModel by viewModels()

    @Inject
    lateinit var aboutAdapter: AboutAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.contacts, ::onViewStateChange)
        setupRecyclerView()
        viewModel.getContacts()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSettings.apply {
            adapter = aboutAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        aboutAdapter.setItemClickListener { selectedAbout ->
            when (selectedAbout.name) {
                "Phone" -> {
                    val callIntent: Intent = Uri.parse("tel:" + selectedAbout.value).let { number ->
                        Intent(Intent.ACTION_DIAL, number)
                    }
                    startActivity(callIntent)
                }
                "email" -> {
                    val emailIntent: Intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("mailto:$selectedAbout.value.toString()")
                    }
                    startActivity(emailIntent)
                }
                else -> showSnackBar(binding.root, selectedAbout.value.toString())
            }
        }
    }

    private fun onViewStateChange(result: AboutUiModel) {
        if (result.isRedelivered) return
        when (result) {
            is AboutUiModel.Loading -> {
                handleLoading(true)
            }
            is AboutUiModel.Success -> {
                handleLoading(false)
                aboutAdapter.list = result.data
            }
            is AboutUiModel.Error -> {
                handleErrorMessage(result.error)
            }
        }
    }
}