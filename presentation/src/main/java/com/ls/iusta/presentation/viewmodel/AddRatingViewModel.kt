package com.ls.iusta.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.auth.AuthUseCase
import com.ls.iusta.domain.interactor.auth.LoginUseCase
import com.ls.iusta.domain.interactor.auth.TokenUseCase
import com.ls.iusta.domain.interactor.worker.GetWorkerInfoUseCase
import com.ls.iusta.domain.interactor.worker.SetWorkerRatingUseCase
import com.ls.iusta.domain.models.auth.AuthUiModel
import com.ls.iusta.domain.models.auth.LoginRequest
import com.ls.iusta.domain.models.auth.LoginUiModel
import com.ls.iusta.domain.models.tickets.TicketDetailUIModel
import com.ls.iusta.domain.models.worker.AddRatingUiModel
import com.ls.iusta.domain.models.worker.SetRatingRequest
import com.ls.iusta.domain.models.worker.WorkerRequest
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.UiAwareLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class AddRatingViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val tokenUseCase: TokenUseCase,
    private val getWorkerInfoUseCase: GetWorkerInfoUseCase,
    private val setWorkerRatingUseCase: SetWorkerRatingUseCase
) : BaseViewModel(contextProvider) {

    private val _ratingData = UiAwareLiveData<AddRatingUiModel>()
    val ratingData: LiveData<AddRatingUiModel> = _ratingData

    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _ratingData.postValue(AddRatingUiModel.Error(exception.message ?: "Error"))
        }

    fun addRating(rating: Int, ticketId: Long, note: String?) {
        _ratingData.postValue(AddRatingUiModel.Loading)
        launchCoroutineIO {
            tokenUseCase(Unit).collect {
                rating(SetRatingRequest(rating, ticketId, note, it))
            }
        }
    }

    private suspend fun rating(data: SetRatingRequest) {
        setWorkerRatingUseCase(data).collect {
            _ratingData.postValue(AddRatingUiModel.Success(it))
        }
    }

    fun getWorkerDetail(workerId: Int) {
        launchCoroutineIO {
            tokenUseCase(Unit).collect {
                loadWorkerDetail(workerId, it)
            }
        }
    }

    private suspend fun loadWorkerDetail(workerId: Int, token: String?) {
        getWorkerInfoUseCase(
            WorkerRequest(
                workerId,
                token
            )
        ).collect {
            _ratingData.postValue(AddRatingUiModel.WorkerInfo(it))
        }
    }

}