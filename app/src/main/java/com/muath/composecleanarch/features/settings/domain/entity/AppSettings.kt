package com.muath.composecleanarch.features.settings.domain.entity

data class AppSettings(
    val themeMode: ThemeMode,
    val language: Language,
    val useDynamicColor: Boolean
)