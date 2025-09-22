package com.muath.composecleanarch.features.settings.presentation

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muath.composecleanarch.features.settings.domain.entity.AppSettings
import com.muath.composecleanarch.features.settings.domain.usecase.GetAppSettingsUseCase
import com.muath.composecleanarch.features.settings.domain.usecase.SetAppSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getSettingsUseCase: GetAppSettingsUseCase,
    val setSettingsUseCase: SetAppSettingsUseCase
): ViewModel() {
    val settings = getSettingsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, AppSettings())

    fun setSettings(settings: AppSettings){
        viewModelScope.launch {
            setSettingsUseCase(settings)
        }
    }

     val canUseDynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}