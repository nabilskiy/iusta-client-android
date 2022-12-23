package com.ls.iusta.presentation.viewmodel.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ls.iusta.domain.interactor.info.GetFaqUseCase
import com.ls.iusta.domain.models.info.FaqUiModel
import com.ls.iusta.domain.models.info.GetInfoRequest
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.PresentationPreferencesHelper
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getFaqUseCase: GetFaqUseCase,
    private val presentationPreferencesHelper: PresentationPreferencesHelper
) : BaseViewModel(contextProvider) {

    private val _faq: MutableLiveData<FaqUiModel> = MutableLiveData()
    val faq: LiveData<FaqUiModel> = _faq

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        /*val message = ExceptionHandler.parse(exception)*/
        _faq.postValue(FaqUiModel.Error(exception.message ?: "Error"))
    }

    fun getFaq() {
        _faq.postValue(FaqUiModel.Loading)
        launchCoroutineIO {
                loadFaq(GetInfoRequest(
                    presentationPreferencesHelper.locale
                ))
        }
    }

    private suspend fun loadFaq(data: GetInfoRequest) {
        getFaqUseCase(
            data
        ).collect {
            _faq.postValue(FaqUiModel.Success(it))
        }
    }

}