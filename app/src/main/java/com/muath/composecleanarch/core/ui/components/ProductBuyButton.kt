package com.muath.composecleanarch.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.muath.composecleanarch.core.model.Product

@Composable
fun ProductBuyButton(
    product: Product,
    modifier: Modifier = Modifier,
    onBuyClick: ((Product) -> Unit)? = null,
) {
    IconButton(
        onClick =  {onBuyClick?.invoke(product)},
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
        )
    ) {
        Icon(
            imageVector =  Icons.Default.ShoppingCart,
            contentDescription = "Buy",
            tint = MaterialTheme.colorScheme.primary
        )
    }

}