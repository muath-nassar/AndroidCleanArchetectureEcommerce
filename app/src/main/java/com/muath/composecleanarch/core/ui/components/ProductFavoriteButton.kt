package com.muath.composecleanarch.core.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import com.muath.composecleanarch.R
import com.muath.composecleanarch.ui.theme.FavoriteColor

@Composable
fun ProductFavoriteButton(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animate size (scale)
    val iconScale by animateFloatAsState(
        targetValue = if (isFavorite) 1.2f else 1.0f,
        animationSpec = tween(durationMillis = 200),
        label = "iconScaleAnimation"
    )
    IconButton(
        onClick = onFavoriteClick,
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
        )
    ) {
        val iconColor = if (isFavorite) FavoriteColor else LocalContentColor.current
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
            contentDescription = if (isFavorite) stringResource(R.string.favorite)
            else stringResource(R.string.unfavorite),
            modifier = Modifier.graphicsLayer(scaleX = iconScale, scaleY = iconScale),
            tint = iconColor
        )
    }
}