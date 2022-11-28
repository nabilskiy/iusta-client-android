package com.ls.iusta.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.GetBookMarkedTicketListUseCase
import com.ls.iusta.domain.interactor.GetTicketListUseCase
import com.ls.iusta.domain.interactor.LoginUseCase
import com.ls.iusta.domain.interactor.UserInfoUseCase
import com.ls.iusta.domain.models.auth.LoginRequest
import com.ls.iusta.domain.models.auth.LoginUiModel
import com.ls.iusta.domain.models.tickets.GetTicketsRequest
import com.ls.iusta.domain.models.tickets.TicketUIModel
import com.ls.iusta.presentation.BuildConfig
import com.ls.iusta.presentation.utils.AppConstants
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.UiAwareLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.crypto.Cipher.SECRET_KEY
import javax.inject.Inject

@HiltViewModel
class TicketsListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val ticketListUseCase: GetTicketListUseCase,
    private val userInfoUseCase: UserInfoUseCase
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
                loadActiveTickets(
                    GetTicketsRequest(
                        AppConstants.Status.OPENED,
                        "8sPdHYouyEm1xhYlzGZM2uN5fmz3hOPm8s1XdkfVaGtO0jzqd49vYhGdskZcIdzd0pqTGMriFc4FMEuP"
                    )
                )
            } else {
                loadTickets(
                    GetTicketsRequest(
                        AppConstants.Status.OPENED,
                        "8sPdHYouyEm1xhYlzGZM2uN5fmz3hOPm8s1XdkfVaGtO0jzqd49vYhGdskZcIdzd0pqTGMriFc4FMEuP"
                    )
                )
            }
        }
    }

    private suspend fun loadTickets(data: GetTicketsRequest) {
        ticketListUseCase(data).collect {
            _ticketList.postValue(TicketUIModel.Success(it))
        }
    }

    private suspend fun loadActiveTickets(data: GetTicketsRequest) {
        ticketListUseCase(data).collect {
            _ticketList.postValue(TicketUIModel.Success(it))
        }
//        bookMarkedTicketListUseCase(Unit).collect {
//            _ticketList.postValue(TicketUIModel.Success(it))
//        }
    }


}