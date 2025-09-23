package com.muath.composecleanarch.features.productlist.presentation.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.muath.composecleanarch.R
import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.core.ui.components.AppBottomSheetDialog
import com.muath.composecleanarch.core.ui.components.GoToCartButton
import com.muath.composecleanarch.core.ui.root.ChromeConfig
import com.muath.composecleanarch.features.cart.presentation.components.CartItemControlView
import com.muath.composecleanarch.features.cart.presentation.components.CartItemViewModel
import com.muath.composecleanarch.features.productlist.presentation.components.ProductGrid

@Composable
fun FavoriteScreen(
    viewModel: FavoriteListViewModel = hiltViewModel(),
    cartItemViewModel: CartItemViewModel = hiltViewModel(),
    showSnackBar: (String, String?) -> Unit,
    applyChrome: (ChromeConfig) -> Unit,
    onProductClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val title = stringResource(R.string.favorite)
    LaunchedEffect(Unit){
        applyChrome(ChromeConfig(title = title, showTopBar = true))
    }
    val favoriteProducts by viewModel.favoriteProducts.collectAsState()
    var buyProduct by remember { mutableStateOf<Product?>(null) }

    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            if (favoriteProducts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_favorite),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                ProductGrid(
                    products = favoriteProducts,
                    onProductClick = { product -> onProductClick(product.id) },
                    viewModel = viewModel,
                    onBuyClick = { product -> buyProduct = product }
                )
                buyProduct?.let {

                    cartItemViewModel.addToCartBtnPressed(it.id) // This line insures at least 1 quantity is added to cart
                    AppBottomSheetDialog(
                        show = true,
                        onDismiss = { buyProduct = null }
                    ) {
                        CartItemControlView(
                            product = it,
                            onDelete = {
                                buyProduct = null
                            },
                        )
                    }
                }

            }

        }
        GoToCartButton(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(12.dp),
            navController = navController
        )
    }
}
