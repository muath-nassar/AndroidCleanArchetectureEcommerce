package com.muath.composecleanarch.core.ui.root

import androidx.compose.runtime.Composable

/**
 * Configuration of the root Scaffold of the app.
 */
data class ChromeConfig(
    val title: String? = null,
    val showTopBar: Boolean = true,
    val showBottomBar: Boolean = true,
    val fab: (@Composable () -> Unit)? = null,
    val navIcon: (@Composable () -> Unit)? = null,
    val actions: (@Composable () -> Unit)? = null,
    )