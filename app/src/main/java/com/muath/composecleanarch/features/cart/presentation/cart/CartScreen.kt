package com.muath.composecleanarch.features.cart.presentation.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.muath.composecleanarch.core.ui.components.ConfirmationDialog
import com.muath.composecleanarch.core.ui.root.ChromeConfig
import com.muath.composecleanarch.features.cart.presentation.components.CartItemRow
import kotlin.String
import com.muath.composecleanarch.R

import kotlin.Unit

@Composable
fun CartScreen(
    applyChrome: (ChromeConfig) -> Unit,
    showSnackBar: (String, String?) -> Unit,
    viewModel: CartScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val cartProducts by viewModel.cartProducts.collectAsState()
    val totalPrice = cartProducts.sumOf { it.product.price * it.quantity }

    var showClearCartDialog by remember { mutableStateOf(false) }

    // Update chrome with Clear Cart action only if cart has items
    val chromeConfig = ChromeConfig(
        title = stringResource(R.string.cart_screen_title),
        showTopBar = true,
        actions = {
            if (cartProducts.isNotEmpty()) {
                IconButton(onClick = { showClearCartDialog = true }) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = stringResource(R.string.cart_clear_cart_description),
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
            title = stringResource(R.string.dialog_clear_cart_title),
            text = stringResource(R.string.dialog_clear_cart_text)
        )
    }

    if (cartProducts.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.cart_is_empty), style = MaterialTheme.typography.bodyLarge)
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
                        text = stringResource(R.string.total_price, totalPrice),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(6.dp))
                    Button(
                        onClick = { showSnackBar("This Application is a Demo. Please contact developer moadnassar@gmail.com", null) }, // Navigate to checkout screen")},
                        modifier = Modifier.fillMaxWidth(),
                        enabled = totalPrice > 0
                    ) {
                        Text(text = stringResource(R.string.proceed_to_checkout), style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}
