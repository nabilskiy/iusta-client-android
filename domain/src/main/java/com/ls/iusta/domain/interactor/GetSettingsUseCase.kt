package com.ls.iusta.domain.interactor

import com.ls.iusta.domain.models.Settings
import com.ls.iusta.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseUseCase<Boolean, Flow<List<Settings>>> {
    override suspend fun invoke(params: Boolean): Flow<List<Settings>> =
        settingsRepository.getSettings(params)
}