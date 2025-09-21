package com.muath.composecleanarch.core.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
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
            Text("${cartProducts.size} item${if(cartProducts.size > 1) "s" else ""} in 🛒")
        }
    }
}