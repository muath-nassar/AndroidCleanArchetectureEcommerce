package com.muath.composecleanarch.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muath.composecleanarch.core.ui.root.ChromeConfig
import com.muath.composecleanarch.features.cart.presentation.cart.CartScreen
import com.muath.composecleanarch.features.productlist.presentation.details.ProductDetailsScreen
import com.muath.composecleanarch.features.productlist.presentation.favorite.FavoriteScreen
import com.muath.composecleanarch.features.productlist.presentation.list.ProductListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    applyChrome: (ChromeConfig) -> Unit = {},
    showSnackBar: (String, String?) -> Unit,
    navController: NavHostController,
    startDestination: String = Routes.PRODUCT_LIST,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // --------- Products Screen ----------------
        composable(route = Routes.PRODUCT_LIST)
        {
            ProductListScreen(
                onProductClick = { id -> navController.navToProductDetailsScreen(id) },
                showSnackBar = showSnackBar,
                applyChrome = applyChrome,
            )
        }

        // --------- Product Details Screen ----------------
        composable(
            route = "${Routes.PRODUCT_DETAILS}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        )
        {
            backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            ProductDetailsScreen(
                productId= productId,
                showSnackBar = showSnackBar,
                applyChrome = applyChrome,
                navController = navController
            )
        }
        // --------------------- Favorite Screen
        composable(
            route = Routes.FAVORITE,
        ){
            FavoriteScreen(
                showSnackBar = showSnackBar,
                applyChrome = applyChrome,
                onProductClick = {
                        id -> navController.navToProductDetailsScreen(id)
                }
            )

        }

        // --------------------- Cart Screen
        composable(
            route = Routes.CART,
        ){
            CartScreen(
                applyChrome = applyChrome,
                showSnackBar = showSnackBar,
            )
        }

    }
}