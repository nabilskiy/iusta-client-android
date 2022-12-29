package com.ls.iusta.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.auth.AuthUseCase
import com.ls.iusta.domain.interactor.auth.LoginUseCase
import com.ls.iusta.domain.interactor.auth.ResetPassUseCase
import com.ls.iusta.domain.models.auth.AuthUiModel
import com.ls.iusta.domain.models.auth.LoginRequest
import com.ls.iusta.domain.models.auth.LoginUiModel
import com.ls.iusta.domain.models.auth.ResetPassUiModel
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.UiAwareLiveData
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ResetPassViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val resetPassUseCase: ResetPassUseCase,
) : BaseViewModel(contextProvider) {

    private val _resetPassData = UiAwareLiveData<ResetPassUiModel>()
    val resetPassData: LiveData<ResetPassUiModel> = _resetPassData

    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _resetPassData.postValue(ResetPassUiModel.Error(exception.message ?: "Error"))
        }

    fun resetPass(email: String) {
        _resetPassData.postValue(ResetPassUiModel.Loading)
        launchCoroutineIO {
            resetPassUseCase(email).collect {
                _resetPassData.postValue(ResetPassUiModel.Success(it))
            }
        }
    }


}