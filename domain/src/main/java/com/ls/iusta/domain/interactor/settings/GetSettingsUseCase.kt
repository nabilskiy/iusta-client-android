package com.ls.iusta.domain.interactor.settings

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.settings.Settings
import com.ls.iusta.domain.models.settings.SettingsRequest
import com.ls.iusta.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseUseCase<SettingsRequest, Flow<List<Settings>>> {
    override suspend fun invoke(params: SettingsRequest): Flow<List<Settings>> =
        settingsRepository.getSettings(params.isNightMode, params.type)
}