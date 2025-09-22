package com.muath.composecleanarch.features.settings.domain.usecase

import com.muath.composecleanarch.features.settings.domain.entity.AppSettings
import com.muath.composecleanarch.features.settings.domain.repo.SettingsRepository
import jakarta.inject.Inject

class SetAppSettingsUseCase@Inject constructor(val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(appSetting: AppSettings){
        settingsRepository.setThemeMode(appSetting.themeMode)
        settingsRepository.setLanguage(appSetting.language)
        settingsRepository.setUseDynamicColor(appSetting.useDynamicColor)

    }
}