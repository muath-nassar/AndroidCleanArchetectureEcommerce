package com.muath.composecleanarch.features.settings.domain.entity

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

enum class Language(val code: String){
    ENGLISH("en"),
    ARABIC("ar")
}

fun Language.getDisplayName(): String {
   return when(this){
       Language.ENGLISH -> "English"
       Language.ARABIC -> "عربي"
   }
}

