package com.ls.iusta.presentation.viewmodel.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ls.iusta.domain.interactor.info.GetTermsUseCase
import com.ls.iusta.domain.models.info.GetInfoRequest
import com.ls.iusta.domain.models.info.TermsUiModel
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.PresentationPreferencesHelper
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getTermsUseCase: GetTermsUseCase,
    private val presentationPreferencesHelper: PresentationPreferencesHelper
) : BaseViewModel(contextProvider) {

    private val _terms: MutableLiveData<TermsUiModel> = MutableLiveData()
    val terms: LiveData<TermsUiModel> = _terms

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        /*val message = ExceptionHandler.parse(exception)*/
        _terms.postValue(TermsUiModel.Error(exception.message ?: "Error"))
    }

    fun getTerms() {
        _terms.postValue(TermsUiModel.Loading)
        launchCoroutineIO {
            loadTerms(
                GetInfoRequest(
                    presentationPreferencesHelper.locale
                )
            )
        }
    }

    private suspend fun loadTerms(data: GetInfoRequest) {
        getTermsUseCase(
            data
        ).collect {
            _terms.postValue(TermsUiModel.Success(it))
        }
    }

}