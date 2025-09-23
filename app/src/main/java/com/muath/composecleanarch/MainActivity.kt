package com.muath.composecleanarch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.muath.composecleanarch.core.ui.root.MyApp
import com.muath.composecleanarch.features.settings.presentation.SettingsViewModel
import com.muath.composecleanarch.ui.LocaleManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val settingsViewModel: SettingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // ✅ Observe language changes and re-apply locale safely
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                settingsViewModel.settings.collect { appSettings ->
                    appSettings?.let {
                        LocaleManager.setLocale(it.language.code)
                    }
                }
            }
        }

        setContent {
            // ✅ Pass VM to MyApp
            MyApp(settingsViewModel)
        }
    }
}

