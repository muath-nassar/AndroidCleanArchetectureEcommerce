package com.muath.composecleanarch.features.cart.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.muath.composecleanarch.R
import com.muath.composecleanarch.core.ui.components.ConfirmationDialog
import com.muath.composecleanarch.features.cart.domain.entity.CartItem
import com.muath.composecleanarch.features.cart.domain.entity.CartProduct

@Composable
fun CartItemRow(
    cartProduct: CartProduct,
    viewModel: CartItemViewModel = hiltViewModel(),
) {
    val product = cartProduct.product
    val quantity = cartProduct.quantity
    val totalPrice = product.price * quantity

    var showDeleteItemDialog by remember { mutableStateOf(false) }

    if (showDeleteItemDialog) {
        ConfirmationDialog(
            onDismissRequest = { showDeleteItemDialog = false },
            onConfirm = {
                viewModel.updateCartItem(CartItem(product.id, 0))
                showDeleteItemDialog = false
            },
            title = stringResource(R.string.dialog_delete_item_title),
            text = stringResource(R.string.dialog_delete_item_text)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product image
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.name,
            placeholder = painterResource(R.drawable.image),
            modifier = Modifier
                .height(56.dp)
                .aspectRatio(1f)
                .padding(end = 12.dp)
        )

        // Product details (name + total price)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = product.name ?: "",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "$${"%.2f".format(totalPrice)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // – / quantity / +
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = {
                    val newQuantity = quantity - 1
                    if (newQuantity == 0){
                        showDeleteItemDialog = true
                    }else{
                        viewModel.updateCartItem(CartItem(product.id, newQuantity))
                    }

                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            ) {
                if (quantity > 1) {
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
                text = "$quantity",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            IconButton(
                onClick = {
                    viewModel.updateCartItem(CartItem(product.id, quantity + 1))
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
