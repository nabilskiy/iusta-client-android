package com.ls.iusta.presentation.viewmodel.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ls.iusta.domain.interactor.info.GetAboutUseCase
import com.ls.iusta.domain.models.info.AboutUiModel
import com.ls.iusta.domain.models.info.GetInfoRequest
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.PresentationPreferencesHelper
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getAboutUseCase: GetAboutUseCase,
    private val presentationPreferencesHelper: PresentationPreferencesHelper
) : BaseViewModel(contextProvider) {

    private val _contacts: MutableLiveData<AboutUiModel> = MutableLiveData()
    val contacts: LiveData<AboutUiModel> = _contacts

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        /*val message = ExceptionHandler.parse(exception)*/
        _contacts.postValue(AboutUiModel.Error(exception.message ?: "Error"))
    }

    fun getContacts() {
        _contacts.postValue(AboutUiModel.Loading)
        launchCoroutineIO {
                loadContacts()
        }
    }

    private suspend fun loadContacts() {
        getAboutUseCase(
           Unit
        ).collect {
            _contacts.postValue(AboutUiModel.Success(it))
        }
    }


}