package com.ls.iusta.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ls.iusta.domain.interactor.user.UserInfoUseCase
import com.ls.iusta.domain.models.settings.SettingUiModel
import com.ls.iusta.domain.models.settings.Settings
import com.ls.iusta.domain.models.user.UserUiModel
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.PresentationPreferencesHelper
import com.ls.iusta.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val userInfoUseCase: UserInfoUseCase,
    private val presentationPreferencesHelper: PresentationPreferencesHelper
) : BaseViewModel(contextProvider) {

    private val _userInfo: MutableLiveData<UserUiModel> = MutableLiveData()
    val userInfo: LiveData<UserUiModel> = _userInfo

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        /*val message = ExceptionHandler.parse(exception)*/
        _userInfo.postValue(UserUiModel.Error(exception.message ?: "Error"))
    }

    fun getUser() {
        _userInfo.postValue(UserUiModel.Loading)
        launchCoroutineIO {
            loadUserInfo()
            getLocale()
        }
    }

    fun updateUser() {
        _userInfo.postValue(UserUiModel.Loading)
        launchCoroutineIO {
            saveUserInfo()
        }
    }

    private suspend fun loadUserInfo() {
        userInfoUseCase(Unit).collect{
            _userInfo.postValue(UserUiModel.Success(it))
        }
    }

    private suspend fun saveUserInfo() {
        //_userInfo.postValue(UserUiModel.SuccessUpdate(it))
    }


    private fun getLocale(){
        _userInfo.postValue(UserUiModel.GetLocale(presentationPreferencesHelper.locale.toString()))
    }

    fun setLocale(lang: String) {
        lang.run {
            presentationPreferencesHelper.locale = lang
            _userInfo.postValue(UserUiModel.ChangeLocale(lang))
        }
    }

}