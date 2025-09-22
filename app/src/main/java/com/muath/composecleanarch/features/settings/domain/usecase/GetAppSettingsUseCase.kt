package com.muath.composecleanarch.features.settings.domain.usecase

import com.muath.composecleanarch.features.settings.domain.entity.AppSettings
import com.muath.composecleanarch.features.settings.domain.repo.SettingsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAppSettingsUseCase@Inject constructor(val settingsRepository: SettingsRepository) {
    operator fun invoke(): Flow<AppSettings> = settingsRepository.settings

}