package com.ls.iusta.presentation.viewmodel.notifications

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.push.DeletePushesUseCase
import com.ls.iusta.domain.interactor.push.GetPushListUseCase
import com.ls.iusta.domain.interactor.push.ReadPushesUseCase
import com.ls.iusta.domain.models.push.MainScreenUIModel
import com.ls.iusta.domain.models.push.NotificationsUIModel
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
    private val pushListUseCase: GetPushListUseCase,
    private val readPushesUseCase: ReadPushesUseCase,
    private val deletePushesUseCase: DeletePushesUseCase
) : BaseViewModel(contextProvider) {

    private val _pushList = UiAwareLiveData<NotificationsUIModel>()
    val pushList: LiveData<NotificationsUIModel> = _pushList

    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _pushList.postValue(NotificationsUIModel.Error(exception.message ?: "Error"))
        }

    fun getPushes(page:Int) {
        _pushList.postValue(NotificationsUIModel.Loading)
        launchCoroutineIO {
            loadPushes(page)
        }
    }

    private suspend fun loadPushes(page:Int) {
        pushListUseCase(page).collect {
            _pushList.postValue(NotificationsUIModel.Success(it))
        }
    }

    fun pushesEdit(ids: String, read: Boolean) {
        launchCoroutineIO {
            if (read) read(ids) else delete(ids)
        }
    }

    private suspend fun read(ids: String) {
        readPushesUseCase(ids).collect {
            _pushList.postValue(NotificationsUIModel.Read(it))
        }
    }

    private suspend fun delete(ids: String) {
        deletePushesUseCase(ids).collect {
            _pushList.postValue(NotificationsUIModel.Delete(it))
        }
    }


}