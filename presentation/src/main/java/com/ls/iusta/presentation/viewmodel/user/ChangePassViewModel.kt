package com.ls.iusta.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ls.iusta.domain.interactor.user.ChangePassUseCase
import com.ls.iusta.domain.models.user.ChangePassUiModel
import com.ls.iusta.domain.models.user.UpdatePassRequest
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ChangePassViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val changePassUseCase: ChangePassUseCase
) : BaseViewModel(contextProvider) {

    private val _settings: MutableLiveData<ChangePassUiModel> = MutableLiveData()
    val settings: LiveData<ChangePassUiModel> = _settings

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        /*val message = ExceptionHandler.parse(exception)*/
        _settings.postValue(ChangePassUiModel.Error(exception.message ?: "Error"))
    }

    fun changePass(
        old_password: String,
        new_password: String,
        new_password_confirmation: String
    ) {
        _settings.postValue(ChangePassUiModel.Loading)
        launchCoroutineIO {
            savePass(old_password, new_password, new_password_confirmation)
        }
    }

    private suspend fun savePass(
        old_password: String,
        new_password: String,
        new_password_confirmation: String
    ) {
        changePassUseCase(
            UpdatePassRequest(
                old_password, new_password, new_password_confirmation
            )
        ).collect {
            _settings.postValue(ChangePassUiModel.Success(it))
        }
    }

}