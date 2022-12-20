package com.ls.iusta.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.auth.AuthUseCase
import com.ls.iusta.domain.interactor.auth.LoginUseCase
import com.ls.iusta.domain.models.auth.AuthUiModel
import com.ls.iusta.domain.models.auth.LoginRequest
import com.ls.iusta.domain.models.auth.LoginUiModel
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
    private val loginUseCase: LoginUseCase,
    private val authUseCase: AuthUseCase
) : BaseViewModel(contextProvider) {

    private val _authData = UiAwareLiveData<AuthUiModel>()
    val authData: LiveData<AuthUiModel> = _authData

    private val _loginData = UiAwareLiveData<LoginUiModel>()
    val loginData: LiveData<LoginUiModel> = _loginData

    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _loginData.postValue(LoginUiModel.Error(exception.message ?: "Error"))
        }

    fun startLogin(email: String, password: String) {
        _loginData.postValue(LoginUiModel.Loading)
        launchCoroutineIO {
            login(LoginRequest(email, password))
        }
    }

    private suspend fun login(data: LoginRequest) {
        loginUseCase(data).collect {
            _loginData.postValue(LoginUiModel.Success(it))
        }
    }

    fun isLogged() {
        _authData.postValue(AuthUiModel.Loading)
        launchCoroutineIO {
            getAuthToken()
        }
    }

    private suspend fun getAuthToken() {
        authUseCase(Unit).collect {
            _authData.postValue(AuthUiModel.Success(it))
        }
    }
}