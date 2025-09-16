package com.muath.composecleanarch.features.productlist.presentation.details

import androidx.compose.runtime.Composable
import com.muath.composecleanarch.core.ui.root.ChromeConfig

@Composable
fun ProductDetailsScreen(
    productId: Int,
    showSnackBar: (String, String?) -> Unit,
    applyChrome: (ChromeConfig) -> Unit
) {
}