package com.ls.iusta.presentation.viewmodel.worker

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.worker.GetWorkerInfoUseCase
import com.ls.iusta.domain.interactor.worker.SetWorkerRatingUseCase
import com.ls.iusta.domain.models.worker.AddRatingUiModel
import com.ls.iusta.domain.models.worker.SetRatingRequest
import com.ls.iusta.domain.models.worker.WorkerRequest
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.UiAwareLiveData
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class AddRatingViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
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
                rating(SetRatingRequest(rating, ticketId, note))

        }
    }

    private suspend fun rating(data: SetRatingRequest) {
        setWorkerRatingUseCase(data).collect {
            _ratingData.postValue(AddRatingUiModel.Success(it))
        }
    }

    fun getWorkerDetail(workerId: Int) {
        launchCoroutineIO {
                loadWorkerDetail(workerId)
        }
    }

    private suspend fun loadWorkerDetail(workerId: Int) {
        getWorkerInfoUseCase(
            WorkerRequest(
                workerId
            )
        ).collect {
            _ratingData.postValue(AddRatingUiModel.WorkerInfo(it))
        }
    }

}