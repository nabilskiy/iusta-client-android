package com.ls.iusta.presentation.viewmodel.tickets

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.auth.TokenUseCase
import com.ls.iusta.domain.interactor.ticket.GetTicketListUseCase
import com.ls.iusta.domain.interactor.user.UserInfoUseCase
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
    private val userInfoUseCase: UserInfoUseCase,
    private val tokenUseCase: TokenUseCase
) : BaseViewModel(contextProvider) {

    private val _ticketList = UiAwareLiveData<TicketUIModel>()
    val ticketList: LiveData<TicketUIModel> = _ticketList

    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _ticketList.postValue(TicketUIModel.Error(exception.message ?: "Error"))
        }

    fun getTickets(active: Boolean) {
        _ticketList.postValue(TicketUIModel.Loading)
        launchCoroutineIO {
            if (active) {
                tokenUseCase(Unit).collect {
                    loadTickets(
                        GetTicketsRequest(
                            AppConstants.Status.OPENED,
                            it
                        )
                    )
                }
            } else {
                tokenUseCase(Unit).collect {
                    loadTickets(
                        GetTicketsRequest(
                            AppConstants.Status.CLOSED,
                            it
                        )
                    )
                }

            }
        }
    }

    private suspend fun loadTickets(data: GetTicketsRequest) {
        ticketListUseCase(data).collect {
            _ticketList.postValue(TicketUIModel.Success(it))
        }
    }


}