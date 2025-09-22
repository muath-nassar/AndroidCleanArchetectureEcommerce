package com.muath.composecleanarch.features.settings.domain.repo



import com.muath.composecleanarch.features.settings.domain.entity.AppSettings
import com.muath.composecleanarch.features.settings.domain.entity.Language
import com.muath.composecleanarch.features.settings.domain.entity.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val settings: Flow<AppSettings>
    suspend fun setThemeMode(mode: ThemeMode)
    suspend fun setLanguage(lang: Language)
    suspend fun setUseDynamicColor(useDynamicColor: Boolean)
}
