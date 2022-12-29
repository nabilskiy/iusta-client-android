package com.ls.iusta.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import com.ls.iusta.domain.interactor.push.SavePushTokenUseCase
import com.ls.iusta.domain.interactor.user.UserInfoUseCase
import com.ls.iusta.domain.models.push.MainScreenUIModel
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.UiAwareLiveData
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val savePushTokenUseCase: SavePushTokenUseCase,
    private val userInfoUseCase: UserInfoUseCase
) : BaseViewModel(contextProvider) {

    private val _mainData = UiAwareLiveData<MainScreenUIModel>()
    val mainData: LiveData<MainScreenUIModel> = _mainData

    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _mainData.postValue(MainScreenUIModel.Error(exception.message ?: "Error"))
        }

    fun saveToken(pushToken: String?, ex: String = "Error") {
        _mainData.postValue(MainScreenUIModel.Loading)
        launchCoroutineIO {
            if (!pushToken.isNullOrBlank())
                savePushTokenUseCase(pushToken).collect {
                    _mainData.postValue(MainScreenUIModel.Token(it))
                }
             else  _mainData.postValue(MainScreenUIModel.Error(ex))
        }
    }

    fun getUserInfo() {
        _mainData.postValue(MainScreenUIModel.Loading)
        launchCoroutineIO {
            userInfoUseCase(Unit).collect {
                    _mainData.postValue(MainScreenUIModel.User(it))
                }
        }
    }

}