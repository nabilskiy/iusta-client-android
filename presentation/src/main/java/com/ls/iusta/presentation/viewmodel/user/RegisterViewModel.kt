package com.ls.iusta.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.auth.RegUseCase
import com.ls.iusta.domain.interactor.customer.GetCustomersListUseCase
import com.ls.iusta.domain.models.auth.*
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.UiAwareLiveData
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getCustomersListUseCase: GetCustomersListUseCase,
    private val regUseCase: RegUseCase
) : BaseViewModel(contextProvider) {

    private val _regData = UiAwareLiveData<RegUiModel>()
    val regData: LiveData<RegUiModel> = _regData

    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _regData.postValue(RegUiModel.Error(exception.message ?: "Error"))
        }

    fun searchCustomer(query: String) {
        _regData.postValue(RegUiModel.Loading)
        launchCoroutineIO {
            customers(query)
        }
    }

    private suspend fun customers(query: String) {
        getCustomersListUseCase(query).collect {
            _regData.postValue(RegUiModel.SuccessSearch(it))
        }
    }

    fun startReg(username: String, password: String, password_confirmation: String, firstname: String,
                 lastname: String, middlename: String, phone_number: String, birthday: String,
                 email: String, il_customer_id: String, language: String) {
        _regData.postValue(RegUiModel.Loading)
        launchCoroutineIO {
            createUser(RegRequest(username, password, password_confirmation, firstname, lastname, middlename, phone_number, birthday, email, il_customer_id, language))
        }
    }

    private suspend fun createUser(data: RegRequest) {
        regUseCase(data).collect {
            _regData.postValue(RegUiModel.Success(it))
        }
    }


}