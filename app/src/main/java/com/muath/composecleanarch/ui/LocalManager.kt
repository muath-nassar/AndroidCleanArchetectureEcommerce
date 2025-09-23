package com.muath.composecleanarch.ui


import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

object LocaleManager {

    fun setLocale(langCode: String) {
        val locale = Locale.forLanguageTag(langCode)
        Locale.setDefault(locale)
        Log.d("setLocale", "setLocale: $langCode")

        // Official way: let AppCompat handle resources & persistence
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(langCode)
        )
    }

    fun getCurrentLocale(): String {
        val appLocales = AppCompatDelegate.getApplicationLocales()
        return appLocales[0]?.language ?: Locale.getDefault().language
    }
}
