package com.ls.iusta.ui.ticketdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.ls.iusta.R
import com.ls.iusta.base.BaseFragment
import com.ls.iusta.databinding.FragmentTicketDetailBinding
import com.ls.iusta.domain.models.Bookmark
import com.ls.iusta.domain.models.TicketDetailUIModel
import com.ls.iusta.extension.observe
import com.ls.iusta.extension.showSnackBar
import com.ls.iusta.presentation.viewmodel.TicketDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TicketDetailFragment :
    BaseFragment<FragmentTicketDetailBinding, TicketDetailViewModel>() {

    override val viewModel: TicketDetailViewModel by viewModels()

    override fun getViewBinding(): FragmentTicketDetailBinding =
        FragmentTicketDetailBinding.inflate(layoutInflater)

    private val args: TicketDetailFragmentArgs by navArgs()

    @Inject
    lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.ticketDetail, ::onViewStateChange)
        viewModel.getTicketDetail(args.ticketId)
        setUiChangeListeners()
    }

    private fun setUiChangeListeners() {
        binding.checkBoxBookmark.setOnCheckedChangeListener { view, isChecked ->
            if (!binding.checkBoxBookmark.isPressed) {
                return@setOnCheckedChangeListener
            }
            if (isChecked)
                viewModel.setBookmarkTicket(view.tag.toString().toLong())
            else
                viewModel.setUnBookmarkTicket(view.tag.toString().toLong())
        }
    }

    private fun onViewStateChange(result: TicketDetailUIModel) {
        if (result.isRedelivered) return
        when (result) {
            is TicketDetailUIModel.Loading -> {
                handleLoading(true)
            }
            is TicketDetailUIModel.Success -> {
                handleLoading(false)
                result.data.let {
                    result.data.let { ticket ->
                        binding.apply {
                            textViewTicketName.text = ticket.name
                            glide.load(ticket.image).into(imageViewTicket)
                            checkBoxBookmark.tag = ticket.id
                            checkBoxBookmark.isChecked = ticket.isBookMarked
                            textViewSpecies.text = ticket.species
                            textViewGender.text = ticket.gender
                            textViewGenderLocation.text = ticket.characterLocation.name
                            textViewStatus.text = ticket.status
                        }
                    }
                }
            }
            is TicketDetailUIModel.Error -> {
                handleErrorMessage(result.error)
            }
            is TicketDetailUIModel.BookmarkStatus -> {
                when (result.bookmark) {
                    Bookmark.BOOKMARK ->
                        if (result.status) {
                            showSnackBar(binding.rootView, getString(R.string.bookmark_success))
                        } else {
                            handleErrorMessage(getString(R.string.bookmark_error))
                        }
                    Bookmark.UN_BOOKMARK ->
                        if (result.status) {
                            showSnackBar(
                                binding.rootView,
                                getString(R.string.un_bookmark_success)
                            )
                        } else {
                            handleErrorMessage(getString(R.string.bookmark_error))
                        }
                }
            }
        }
    }
}