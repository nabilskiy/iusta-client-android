package com.ls.iusta.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.auth.TokenUseCase
import com.ls.iusta.domain.interactor.ticket.CreateNoteUseCase
import com.ls.iusta.domain.interactor.ticket.GetTicketByIdUseCase
import com.ls.iusta.domain.interactor.ticket.SetTicketBookmarkUseCase
import com.ls.iusta.domain.interactor.ticket.SetTicketUnBookmarkUseCase
import com.ls.iusta.domain.interactor.worker.GetWorkerInfoUseCase
import com.ls.iusta.domain.interactor.worker.GetWorkerRatingUseCase
import com.ls.iusta.domain.models.tickets.CreateNoteRequest
import com.ls.iusta.domain.models.tickets.GetTicketByIdRequest
import com.ls.iusta.domain.models.tickets.TicketDetailUIModel
import com.ls.iusta.domain.models.worker.GetRatingRequest
import com.ls.iusta.domain.models.worker.WorkerRequest
import com.ls.iusta.presentation.utils.AppConstants
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
    private val getWorkerInfoUseCase: GetWorkerInfoUseCase,
    private val tokenUseCase: TokenUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val getWorkerRatingUseCase: GetWorkerRatingUseCase
) : BaseViewModel(contextProvider) {

    private val _ticketDetail = UiAwareLiveData<TicketDetailUIModel>()
    val ticketDetail: LiveData<TicketDetailUIModel> = _ticketDetail

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _ticketDetail.postValue(TicketDetailUIModel.Error(exception.message ?: "Error"))
    }

    fun getTicketDetail(ticketId: Long) {
        launchCoroutineIO {
            tokenUseCase(Unit).collect {
                loadTicketDetail(ticketId, it)
            }
        }
    }

    fun getWorkerDetail(workerId: Int) {
        launchCoroutineIO {
            tokenUseCase(Unit).collect {
                loadWorkerDetail(workerId, it)
            }
        }
    }

    private suspend fun loadTicketDetail(ticketId: Long, token: String?) {
        _ticketDetail.postValue(TicketDetailUIModel.Loading)
        ticketByIdUseCase(
            GetTicketByIdRequest(
                AppConstants.Status.OPENED,
                token,
                ticketId
            )
        ).collect {
            _ticketDetail.postValue(TicketDetailUIModel.Success(it))
        }
    }

    private suspend fun loadWorkerDetail(workerId: Int, token: String?) {
        getWorkerInfoUseCase(
            WorkerRequest(
                workerId,
                token
            )
        ).collect {
            _ticketDetail.postValue(TicketDetailUIModel.WorkerInfo(it))
        }
    }

    fun sendNoteForTicket(ticketId: Long, ticketNote: String?) {
        launchCoroutineIO {
            tokenUseCase(Unit).collect {
                addNote(ticketId, ticketNote, it)
            }
        }
    }

    private suspend fun addNote(ticketId: Long, ticketNote: String?, token: String?) {
        _ticketDetail.postValue(TicketDetailUIModel.Loading)
        createNoteUseCase(
            CreateNoteRequest(
                ticketId,
                ticketNote,
                token
            )
        ).collect {
            _ticketDetail.postValue(TicketDetailUIModel.AddNote(it))
        }
    }


    fun checkRating(ticketId: Long) {
        launchCoroutineIO {
            tokenUseCase(Unit).collect {
                getRating(ticketId, it)
            }
        }
    }

    private suspend fun getRating(ticketId: Long, token: String?) {
        getWorkerRatingUseCase(
            GetRatingRequest(
                ticketId,
                token
            )
        ).collect {
            _ticketDetail.postValue(TicketDetailUIModel.GetRating(it))
        }
    }

}