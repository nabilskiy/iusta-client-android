package com.ls.iusta.presentation.viewmodel.tickets

import android.util.Log
import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.auth.LogoutForceUseCase
import com.ls.iusta.domain.interactor.auth.LogoutUseCase
import com.ls.iusta.domain.interactor.ticket.GetTicketListUseCase
import com.ls.iusta.domain.models.settings.SettingUiModel
import com.ls.iusta.domain.models.tickets.GetTicketsRequest
import com.ls.iusta.domain.models.tickets.TicketUIModel
import com.ls.iusta.presentation.utils.AppConstants
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.UiAwareLiveData
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class TicketsListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val ticketListUseCase: GetTicketListUseCase,
    private val logoutUseCase: LogoutForceUseCase,
) : BaseViewModel(contextProvider) {

    private val _ticketList = UiAwareLiveData<TicketUIModel>()
    val ticketList: LiveData<TicketUIModel> = _ticketList

    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _ticketList.postValue(TicketUIModel.Error(exception.message ?: "Error"))
        }

    fun getTickets(active: Boolean, page: Int) {
        Log.d("getTickets", " page: "+page)
        _ticketList.postValue(TicketUIModel.Loading)
        launchCoroutineIO {
            loadTickets(
                GetTicketsRequest(
                    active, page
                )
            )
        }
    }

    private suspend fun loadTickets(data: GetTicketsRequest) {
        ticketListUseCase(data).collect {
            _ticketList.postValue(TicketUIModel.Success(it))
        }
    }

    fun logout() {
        _ticketList.postValue(TicketUIModel.Loading)
        launchCoroutineIO {
            logoutUseCase(Unit).collect {
                _ticketList.postValue(TicketUIModel.Logout(it))
            }
        }
    }

}