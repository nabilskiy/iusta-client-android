package com.ls.iusta.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.GetTicketByIdUseCase
import com.ls.iusta.domain.interactor.SetTicketBookmarkUseCase
import com.ls.iusta.domain.interactor.SetTicketUnBookmarkUseCase
import com.ls.iusta.domain.models.Bookmark
import com.ls.iusta.domain.models.TicketDetailUIModel
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.UiAwareLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class TicketDetailViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val ticketByIdUseCase: GetTicketByIdUseCase,
    private val setTicketBookmarkUseCase: SetTicketBookmarkUseCase,
    private val setTicketUnBookmarkUseCase: SetTicketUnBookmarkUseCase
) : BaseViewModel(contextProvider) {

    private val _ticketDetail = UiAwareLiveData<TicketDetailUIModel>()
    val ticketDetail: LiveData<TicketDetailUIModel> = _ticketDetail

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _ticketDetail.postValue(TicketDetailUIModel.Error(exception.message ?: "Error"))
    }

    fun getTicketDetail(ticketId: Long) {
        launchCoroutineIO {
            loadTicketDetail(ticketId)
        }
    }

    private suspend fun loadTicketDetail(ticketId: Long) {
        _ticketDetail.postValue(TicketDetailUIModel.Loading)
        ticketByIdUseCase(ticketId).collect {
            _ticketDetail.postValue(TicketDetailUIModel.Success(it))
        }
    }

    fun setBookmarkTicket(ticketId: Long) {
        launchCoroutineIO {
            setTicketBookmarkUseCase(ticketId).collect {
                if (it == 1) {
                    _ticketDetail.postValue(
                        TicketDetailUIModel.BookmarkStatus(
                            Bookmark.BOOKMARK,
                            true
                        )
                    )
                } else {
                    _ticketDetail.postValue(
                        TicketDetailUIModel.BookmarkStatus(
                            Bookmark.BOOKMARK,
                            false
                        )
                    )
                }
            }
        }
    }

    fun setUnBookmarkTicket(ticketId: Long) {
        launchCoroutineIO {
            setTicketUnBookmarkUseCase(ticketId).collect {
                if (it == 1) {
                    _ticketDetail.postValue(
                        TicketDetailUIModel.BookmarkStatus(
                            Bookmark.UN_BOOKMARK,
                            true
                        )
                    )
                } else {
                    _ticketDetail.postValue(
                        TicketDetailUIModel.BookmarkStatus(
                            Bookmark.UN_BOOKMARK,
                            false
                        )
                    )
                }
            }
        }
    }
}