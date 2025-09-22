package com.muath.composecleanarch.features.settings.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.muath.composecleanarch.features.settings.domain.entity.AppSettings
import com.muath.composecleanarch.features.settings.domain.entity.Language
import com.muath.composecleanarch.features.settings.domain.entity.ThemeMode
import com.muath.composecleanarch.features.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImp(private val context: Context): SettingsRepository {

    override val settings: Flow<AppSettings> =
        context.settingsDataStore.data.map { preferences ->
            AppSettings(
                themeMode = ThemeMode.valueOf(
                    preferences[SettingsKeys.THEME_MODE] ?: ThemeMode.SYSTEM.name
                ),
                language = Language.valueOf(
                    preferences[SettingsKeys.LANGUAGE] ?: Language.ENGLISH.name
                ),
                useDynamicColor = preferences[SettingsKeys.USE_DYNAMIC_COLOR] ?: false
            )
        }


    override suspend fun setThemeMode(mode: ThemeMode) {
        context.settingsDataStore.edit { it[SettingsKeys.THEME_MODE] = mode.name }
    }


    override suspend fun setLanguage(lang: Language) {
        context.settingsDataStore.edit { it[SettingsKeys.LANGUAGE] = lang.name }
    }

    override suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
        context.settingsDataStore.edit { it[SettingsKeys.USE_DYNAMIC_COLOR] = useDynamicColor }
    }
}