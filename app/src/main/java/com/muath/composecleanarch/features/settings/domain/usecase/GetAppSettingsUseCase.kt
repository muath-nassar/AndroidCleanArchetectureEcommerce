package com.muath.composecleanarch.features.settings.domain.usecase

import com.muath.composecleanarch.features.settings.domain.repo.SettingsRepository
import jakarta.inject.Inject

class GetAppSettingsUseCase@Inject constructor(val settingsRepository: SettingsRepository) {
    operator fun invoke() = settingsRepository.settings

}