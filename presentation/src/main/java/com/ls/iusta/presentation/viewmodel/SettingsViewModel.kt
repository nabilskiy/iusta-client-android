package com.ls.iusta.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ls.iusta.domain.interactor.settings.GetSettingsUseCase
import com.ls.iusta.domain.models.settings.SettingUiModel
import com.ls.iusta.domain.models.settings.Settings
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import com.ls.iusta.presentation.utils.PresentationPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val presentationPreferencesHelper: PresentationPreferencesHelper
) : BaseViewModel(contextProvider) {

    private val _settings: MutableLiveData<SettingUiModel> = MutableLiveData()
    val settings: LiveData<SettingUiModel> = _settings

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        /*val message = ExceptionHandler.parse(exception)*/
        _settings.postValue(SettingUiModel.Error(exception.message ?: "Error"))
    }

    fun getSettings() {
        _settings.postValue(SettingUiModel.Loading)
        launchCoroutineIO {
            loadSettings()
        }
    }

    private suspend fun loadSettings() {
        getSettingsUseCase(presentationPreferencesHelper.isNightMode).collect {
            _settings.postValue(SettingUiModel.Success(it))
        }
    }

    fun setSettings(selectedSetting: Settings) {
        selectedSetting.run {
            presentationPreferencesHelper.isNightMode = selectedValue
            _settings.postValue(SettingUiModel.NightMode(selectedValue))
        }
    }
}