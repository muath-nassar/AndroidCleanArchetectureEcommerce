package com.muath.composecleanarch.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.muath.composecleanarch.R
import com.muath.composecleanarch.features.settings.domain.entity.Language


// for eng
val InterFontFamily = FontFamily(
    Font(R.font.inter_18pt_regular, FontWeight.Normal),
    Font(R.font.inter_18pt_medium, FontWeight.Medium),
    Font(R.font.inter_18pt_bold, FontWeight.Bold),
    Font(R.font.inter_18pt_light, FontWeight.Light),
    Font(R.font.inter_18pt_thin, FontWeight.Thin),
    Font(R.font.inter_18pt_extrabold, FontWeight.ExtraBold),
    Font(R.font.inter_18pt_black, FontWeight.Black),
    Font(R.font.inter_18pt_semibold, FontWeight.SemiBold),
    Font(R.font.inter_18pt_extralight, FontWeight.ExtraLight),
    Font(R.font.inter_18pt_italic, FontWeight.Normal) // italic variant
)

//Cairo font family (Arabic)
val CairoFontFamily = FontFamily(
    Font(R.font.cairo_regular, FontWeight.Normal),
    Font(R.font.cairo_medium, FontWeight.Medium),
    Font(R.font.cairo_bold, FontWeight.Bold),
    Font(R.font.cairo_light, FontWeight.Light),
    Font(R.font.cairo_extralight, FontWeight.ExtraLight),
    Font(R.font.cairo_semibold, FontWeight.SemiBold),
    Font(R.font.cairo_extrabold, FontWeight.ExtraBold),
    Font(R.font.cairo_black, FontWeight.Black)
)

/**
 * Professional, readable scale tuned for app UIs (not oversized like stock M3 display sizes).
 * - Inter for EN, Cairo for AR
 * - Arabic letter spacing set to 0.sp
 * - Subtle line heights for good rhythm
 */
fun appTypography(language: Language): Typography {
    val ff: FontFamily = when (language) {
        Language.ENGLISH -> InterFontFamily
        Language.ARABIC  -> CairoFontFamily
    }

    // Letter spacing: keep 0 for Arabic; modest tracking for Latin
    val titleLs   = if (language == Language.ARABIC) 0.sp else 0.sp
    val headlineLs= if (language == Language.ARABIC) 0.sp else 0.sp
    val bodyLs    = if (language == Language.ARABIC) 0.sp else 0.25.sp
    val labelLs   = if (language == Language.ARABIC) 0.sp else 0.4.sp

    return Typography(
        // Slightly reduced “display” sizes to suit phones
        displayLarge = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.Bold,
            fontSize = 44.sp, lineHeight = 52.sp, letterSpacing = 0.sp
        ),
        displayMedium = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.SemiBold,
            fontSize = 36.sp, lineHeight = 44.sp, letterSpacing = 0.sp
        ),
        displaySmall = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp, lineHeight = 40.sp, letterSpacing = 0.sp
        ),

        headlineLarge = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp, lineHeight = 36.sp, letterSpacing = headlineLs
        ),
        headlineMedium = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.Medium,
            fontSize = 24.sp, lineHeight = 32.sp, letterSpacing = headlineLs
        ),
        headlineSmall = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.Medium,
            fontSize = 22.sp, lineHeight = 30.sp, letterSpacing = headlineLs
        ),

        titleLarge = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp, lineHeight = 28.sp, letterSpacing = titleLs
        ),
        titleMedium = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.Medium,
            fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = titleLs
        ),
        titleSmall = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.Medium,
            fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = titleLs
        ),

        bodyLarge = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.Normal,
            fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = bodyLs
        ),
        bodyMedium = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.Normal,
            fontSize = 14.sp, lineHeight = 22.sp, letterSpacing = bodyLs
        ),
        bodySmall = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.Normal,
            fontSize = 13.sp, lineHeight = 20.sp, letterSpacing = bodyLs
        ),

        labelLarge = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.Medium,
            fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = labelLs
        ),
        labelMedium = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.Medium,
            fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = labelLs
        ),
        labelSmall = TextStyle(
            fontFamily = ff, fontWeight = FontWeight.Medium,
            fontSize = 11.sp, lineHeight = 14.sp, letterSpacing = labelLs
        )
    )
}