package com.ls.iusta.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.GetBookMarkedTicketListUseCase
import com.ls.iusta.domain.interactor.GetTicketListUseCase
import com.ls.iusta.domain.models.TicketUIModel
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.UiAwareLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class TicketsListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val ticketListUseCase: GetTicketListUseCase,
    private val bookMarkedTicketListUseCase: GetBookMarkedTicketListUseCase
) : BaseViewModel(contextProvider) {

    private val _ticketList = UiAwareLiveData<TicketUIModel>()
    val ticketList: LiveData<TicketUIModel> = _ticketList

    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _ticketList.postValue(TicketUIModel.Error(exception.message ?: "Error"))
        }

    fun getTickets(isFavorite: Boolean) {
        _ticketList.postValue(TicketUIModel.Loading)
        launchCoroutineIO {
            if (isFavorite) {
                loadFavoriteTickets()
            } else {
                loadTickets()
            }
        }
    }

    private suspend fun loadTickets() {
        ticketListUseCase(Unit).collect {
            _ticketList.postValue(TicketUIModel.Success(it))
        }
    }

    private suspend fun loadFavoriteTickets() {
        bookMarkedTicketListUseCase(Unit).collect {
            _ticketList.postValue(TicketUIModel.Success(it))
        }
    }
}