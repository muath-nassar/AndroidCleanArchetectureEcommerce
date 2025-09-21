package com.muath.composecleanarch.features.productlist.presentation.list

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.core.ui.RefreshableContainer
import com.muath.composecleanarch.core.ui.RenderAppError
import com.muath.composecleanarch.core.ui.components.AppBottomSheetDialog
import com.muath.composecleanarch.core.ui.components.GoToCartButton
import com.muath.composecleanarch.core.ui.root.ChromeConfig
import com.muath.composecleanarch.features.cart.presentation.components.CartItemControlView
import com.muath.composecleanarch.features.cart.presentation.components.CartItemViewModel
import com.muath.composecleanarch.features.productlist.presentation.components.ProductGrid
import com.muath.composecleanarch.features.productlist.presentation.components.ProductGridShimmer


@Composable
fun ProductListScreen(
    onProductClick: (Int) -> Unit,
    navController: NavController,
    showSnackBar: (String, String?) -> Unit,
    applyChrome: (ChromeConfig) -> Unit,
    productListViewModel: ProductListViewModel = hiltViewModel<ProductListViewModel>(),
    cartItemViewModel: CartItemViewModel = hiltViewModel<CartItemViewModel>()
) {
    val uiState by productListViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        applyChrome(ChromeConfig(title = "Products", showTopBar = true))
    }
    // This variable to hold the state for Bottom Sheet
    var buyProduct by remember { mutableStateOf<Product?>(null) }
    Column(
    ){
        // Filter by Category
        CategoryChips(
            uiState.categoryList,
            selected = uiState.category,
            onCategorySelected = {newSelected ->
                productListViewModel.loadProducts(newSelected)
            })
        Box(modifier= Modifier
            .weight(1f)
        ){
            when{
                uiState.isLoading ->{
                    ProductGridShimmer()
                }
                uiState.error != null ->{
                    RenderAppError(error = uiState.error, onDismiss = {})
                }
                else ->{
                    Box (modifier = Modifier){
                        RefreshableContainer(
                            isRefreshing = uiState.isLoading,
                            onRefresh = { productListViewModel.loadProducts(uiState.category) }
                        ) {
                            ProductGrid(
                                products = uiState.products,
                                onProductClick = { product -> onProductClick(product.id) },
                                modifier = Modifier.fillMaxSize(),
                                viewModel = productListViewModel,
                                onBuyClick = { product ->
                                    buyProduct = product
                                }
                            )
                        }
                    }
                }
            }

            buyProduct?.let {

                cartItemViewModel.addToCartBtnPressed(it.id) // This line insures at least 1 quantity is added to cart
                AppBottomSheetDialog(
                    show = true,
                    onDismiss = {buyProduct = null}
                ){
                    CartItemControlView(
                        product = it,
                        onDelete = {
                            buyProduct = null
                        },
                    )
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

}

@Composable
fun CategoryChips(
    categories: List<String>,
    selected: String?,
    onCategorySelected: (String?) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item{Spacer(Modifier.width(5.dp))}
        // "All" filter
        item {
            FilterChip(
                selected = selected == null,
                onClick = { onCategorySelected(null) },
                label = { Text("All", style = MaterialTheme.typography.bodyMedium) }
            )
        }

        // Category filters
        items(categories) { category ->
            FilterChip(
                selected = selected == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category, style = MaterialTheme.typography.bodyMedium) }
            )
        }
        item{Spacer(Modifier.width(5.dp))}

    }
}