package com.muath.composecleanarch.core.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.muath.composecleanarch.features.settings.presentation.SettingsViewModel
import com.muath.composecleanarch.ui.theme.ComposeCleanArchTheme

@Composable
fun MyApp(viewModel: SettingsViewModel = hiltViewModel()) {
    val settings by viewModel.settings.collectAsState(null)

    settings?.let{
        ComposeCleanArchTheme(
            themeMode = it.themeMode,
            useDynamicColor = it.useDynamicColor,
        ) {
            RootScaffold()
        }
    }

}
