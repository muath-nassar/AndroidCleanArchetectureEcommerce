package com.muath.composecleanarch.features.settings.domain.entity

data class AppSettings(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val language: Language = Language.ENGLISH,
    val useDynamicColor: Boolean = true
)