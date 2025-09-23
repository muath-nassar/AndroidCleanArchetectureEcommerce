package com.muath.composecleanarch.features.settings.presentation

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muath.composecleanarch.features.settings.domain.entity.AppSettings
import com.muath.composecleanarch.features.settings.domain.entity.Language
import com.muath.composecleanarch.features.settings.domain.usecase.GetAppSettingsUseCase
import com.muath.composecleanarch.features.settings.domain.usecase.SetAppSettingsUseCase
import com.muath.composecleanarch.ui.LocaleManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@HiltViewModel
class SettingsViewModel @Inject constructor(
    getSettingsUseCase: GetAppSettingsUseCase,
    private val setSettingsUseCase: SetAppSettingsUseCase
) : ViewModel() {

    val settings = getSettingsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun setSettings(settings: AppSettings) {
        viewModelScope.launch {
            setSettingsUseCase(settings)
        }
    }

    fun setLanguage(language: Language) {
        viewModelScope.launch {

            settings.value?.let {
                val newSettings = it.copy(language = language)
                setSettingsUseCase(newSettings)           // ✅ persist in DataStore
                LocaleManager.setLocale(language.code)    // ✅ apply immediately

            }
        }
    }

    val canUseDynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}
