package com.muath.composecleanarch.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.muath.composecleanarch.features.settings.domain.entity.Language
import com.muath.composecleanarch.features.settings.domain.entity.ThemeMode

private val LightColorScheme = lightColorScheme(
    primary = EmeraldGreen,
    onPrimary = LightOnPrimary,
    secondary = SlateGray,
    onSecondary = LightOnSecondary,
    tertiary = Amber,
    background = LightBackground,
    onBackground = DarkText,
    surface = LightSurface,
    onSurface = DarkText,
)

private val DarkColorScheme = darkColorScheme(
    primary = EmeraldGreenLight,
    onPrimary = DarkOnPrimary,
    secondary = SlateGrayLight,
    onSecondary = DarkOnSecondary,
    tertiary = AmberLight,
    background = DarkBackground,
    onBackground = LightText,
    surface = DarkSurface,
    onSurface = LightText,
)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */

// Dynamic color is available on Android 12+
@Composable
fun ComposeCleanArchTheme(
    themeMode: ThemeMode,
    useDynamicColor: Boolean = false,
    language: Language,
    content: @Composable () -> Unit
) {

    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    val canUseDynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colorScheme = when {
        useDynamicColor && canUseDynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = appTypography(language),
        content = content
    )
}