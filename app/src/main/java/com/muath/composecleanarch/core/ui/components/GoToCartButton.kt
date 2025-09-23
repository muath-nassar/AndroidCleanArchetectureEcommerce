package com.muath.composecleanarch.core.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.muath.composecleanarch.R
import com.muath.composecleanarch.core.ui.navigation.Routes
import com.muath.composecleanarch.features.cart.presentation.cart.CartScreenViewModel

@Composable
fun GoToCartButton(
    modifier: Modifier = Modifier,
    viewModel: CartScreenViewModel = hiltViewModel(),
    navController: NavController

) {
    val cartProducts by viewModel.cartProducts.collectAsState()
    if (cartProducts.isNotEmpty()){
        Button(
            modifier = modifier,
            onClick = {
                navController.navigate(Routes.CART) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        ){
            val buttonText = LocalContext.current.resources.getQuantityString(
                R.plurals.cart_button_item_count, // The plural resource ID
                cartProducts.size,                         // The quantity to decide which string to use
                cartProducts.size                          // The value for the %d placeholder
            )
            Text(buttonText)
        }
    }
}