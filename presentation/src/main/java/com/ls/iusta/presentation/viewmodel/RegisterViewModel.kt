package com.ls.iusta.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.auth.AuthUseCase
import com.ls.iusta.domain.interactor.auth.LoginUseCase
import com.ls.iusta.domain.interactor.auth.RegUseCase
import com.ls.iusta.domain.interactor.customer.GetCustomersListUseCase
import com.ls.iusta.domain.models.auth.AuthUiModel
import com.ls.iusta.domain.models.auth.LoginRequest
import com.ls.iusta.domain.models.auth.LoginUiModel
import com.ls.iusta.domain.models.customer.CustomerUiModel
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.UiAwareLiveData
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

    private val _customersData = UiAwareLiveData<CustomerUiModel>()
    val customersData: LiveData<CustomerUiModel> = _customersData

    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _customersData.postValue(CustomerUiModel.Error(exception.message ?: "Error"))
        }

    fun searchCustomer(query: String) {
        _customersData.postValue(CustomerUiModel.Loading)
        launchCoroutineIO {
            customers(query)
        }
    }

    private suspend fun customers(query: String) {
        getCustomersListUseCase(query).collect {
            _customersData.postValue(CustomerUiModel.Success(it))
        }
    }


}