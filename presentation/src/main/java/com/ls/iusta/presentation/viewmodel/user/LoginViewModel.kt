package com.ls.iusta.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.auth.AuthUseCase
import com.ls.iusta.domain.interactor.auth.LoginUseCase
import com.ls.iusta.domain.models.auth.LoginRequest
import com.ls.iusta.domain.models.auth.LoginUiModel
import com.ls.iusta.presentation.R
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.UiAwareLiveData
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val loginUseCase: LoginUseCase,
    private val authUseCase: AuthUseCase
) : BaseViewModel(contextProvider) {

    private val _loginData = UiAwareLiveData<LoginUiModel>()
    val loginData: LiveData<LoginUiModel> = _loginData

    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _loginData.postValue(LoginUiModel.Error(exception.message ?: "Error"))
        }

    fun startLogin(email: String, password: String) {
        _loginData.postValue(LoginUiModel.Loading)
        if (checkEmail(email) && checkPassword(password)) {
            launchCoroutineIO {
                login(LoginRequest(email, password))
            }
        }
    }

    private suspend fun login(data: LoginRequest) {
        loginUseCase(data).collect {
            _loginData.postValue(LoginUiModel.Success(it))
        }
    }

    fun isLogged() {
        _loginData.postValue(LoginUiModel.Loading)
        launchCoroutineIO {
            getAuthToken()
        }
    }

    private suspend fun getAuthToken() {
        authUseCase(Unit).collect {
            _loginData.postValue(LoginUiModel.CheckLogin(it))
        }
    }

    // Check email field
    private fun checkEmail(email: String): Boolean {
        var isValid = true
        when {
            email.isEmpty() -> {
                _loginData.postValue(LoginUiModel.EmptyEmail)
                isValid = false
            }
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                _loginData.postValue(LoginUiModel.IncorrectEmail)
                isValid = false
            }
        }
        return isValid
    }

    // Check password field
    private fun checkPassword(password: String): Boolean {
        var isValid = true
        if (password.isEmpty()) {
            _loginData.postValue(LoginUiModel.PasswordError)
            isValid = false
        }
        return isValid
    }
}