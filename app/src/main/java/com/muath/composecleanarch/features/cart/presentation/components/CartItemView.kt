package com.muath.composecleanarch.features.cart.presentation.components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.muath.composecleanarch.R
import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.features.cart.domain.entity.CartItem

@Composable
fun CartItemControlView(
    product: Product,
    onDelete: (() -> Unit)? = null,
    viewModel: CartItemViewModel = hiltViewModel(),
) {
    val cartItem by viewModel.subscribeToCartItem(product.id).collectAsState(null)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        product.name?.let {
            Text(it, style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp))
        }
        Spacer(Modifier.height(5.dp))

        Text("$ ${product.price * (cartItem?.quantity ?: 0)}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
            )

        Spacer(Modifier.height(5.dp))
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,

        ) {
            // Decrease / Remove button
            IconButton(
                onClick = {
                    cartItem?.let {
                        val newQuantity = it.quantity - 1
                        viewModel.updateCartItem(it.copy(quantity = newQuantity))
                        if(newQuantity == 0){
                            onDelete?.invoke()
                        }
                    }
                },
                enabled = cartItem != null,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            ) {
                if ((cartItem?.quantity ?: 0) > 1) {
                    Icon(
                        painter = painterResource(R.drawable.remove),
                        contentDescription = "Decrease quantity",
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove from cart",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Text(
                text = "${cartItem?.quantity ?: 0}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 8.dp)
            )

// Increase button
            IconButton(
                onClick = {
                    if (cartItem != null) {
                        viewModel.updateCartItem(cartItem!!.copy(quantity = cartItem!!.quantity + 1))
                    } else {
                        // item is not in cart yet, add with quantity = 1
                        viewModel.updateCartItem(CartItem(product.id, 1))
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase quantity",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }
    }


}
