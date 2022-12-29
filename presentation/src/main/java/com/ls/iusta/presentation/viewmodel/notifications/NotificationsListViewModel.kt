package com.ls.iusta.presentation.viewmodel.notifications

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.push.GetPushListUseCase
import com.ls.iusta.domain.models.push.MainScreenUIModel
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.UiAwareLiveData
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class NotificationsListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val pushListUseCase: GetPushListUseCase
) : BaseViewModel(contextProvider) {

    private val _pushList = UiAwareLiveData<MainScreenUIModel>()
    val pushList: LiveData<MainScreenUIModel> = _pushList

    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _pushList.postValue(MainScreenUIModel.Error(exception.message ?: "Error"))
        }

    fun getPushes() {
        _pushList.postValue(MainScreenUIModel.Loading)
        launchCoroutineIO {
            loadPushes()
        }
    }

    private suspend fun loadPushes() {
        pushListUseCase(Unit).collect {
            _pushList.postValue(MainScreenUIModel.Success(it))
        }
    }


}