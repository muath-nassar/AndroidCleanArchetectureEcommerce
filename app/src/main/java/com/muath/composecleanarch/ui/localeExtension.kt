package com.muath.composecleanarch.ui

import android.content.Context
import java.util.Locale
import com.muath.composecleanarch.features.settings.domain.entity.Language

fun Context.updateLocale(language: Language): Context {
    val locale = Locale.forLanguageTag(language.code)
    Locale.setDefault(locale)

    val config = resources.configuration
    config.setLocale(locale)

    return createConfigurationContext(config)
}
