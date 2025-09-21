package com.muath.composecleanarch.features.cart.presentation.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.muath.composecleanarch.core.ui.root.ChromeConfig
import com.muath.composecleanarch.features.cart.presentation.components.CartItemRow

@Composable
fun CartScreen(
    applyChrome: (ChromeConfig) -> Unit,
    viewModel: CartScreenViewModel = hiltViewModel(),
) {
    val cartProducts by viewModel.cartProducts.collectAsState()
    val totalPrice = cartProducts.sumOf { it.product.price * it.quantity }

    var showClearCartDialog by remember { mutableStateOf(false) }

    // Update chrome with Clear Cart action only if cart has items
    val chromeConfig = ChromeConfig(
        title = "Cart",
        showTopBar = true,
        actions = {
            if (cartProducts.isNotEmpty()) {
                IconButton(onClick = { showClearCartDialog = true }) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Clear Cart",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
    applyChrome(chromeConfig)

    if (showClearCartDialog) {
        ConfirmationDialog(
            onDismissRequest = { showClearCartDialog = false },
            onConfirm = {
                viewModel.clearCart()
                showClearCartDialog = false
            },
            title = "Clear Cart",
            text = "Are you sure you want to remove all items from your cart?"
        )
    }

    if (cartProducts.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cart is empty 🛒")
        }
    } else {
        Column {
            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cartProducts) { cartProduct ->
                        CartItemRow(cartProduct = cartProduct)
                    }
                }
            }

            // Bottom summary & checkout
            Box(modifier = Modifier.padding(12.dp)) {
                Column {
                    Text(
                        text = "Total Price: $${"%.2f".format(totalPrice)}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(6.dp))
                    Button(
                        onClick = { /* TODO: navigate to checkout */ },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = totalPrice > 0
                    ) {
                        Text("Proceed to Checkout", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}



@Composable
fun ConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    text: String
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        text = { Text(text = text) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}
