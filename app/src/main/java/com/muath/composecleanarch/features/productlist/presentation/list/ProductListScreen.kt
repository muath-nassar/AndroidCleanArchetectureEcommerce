package com.muath.composecleanarch.features.productlist.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.muath.composecleanarch.core.ui.RefreshableContainer
import com.muath.composecleanarch.core.ui.RenderAppError
import com.muath.composecleanarch.core.ui.root.ChromeConfig
import com.muath.composecleanarch.features.productlist.presentation.components.ProductGrid
import com.muath.composecleanarch.features.productlist.presentation.components.ProductGridShimmer


@Composable
fun ProductListScreen(
    onProductClick: (Int) -> Unit,
    showSnackBar: (String, String?) -> Unit,
    applyChrome: (ChromeConfig) -> Unit,
    productListViewModel: ProductListViewModel = hiltViewModel<ProductListViewModel>()
) {
    val uiState by productListViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        applyChrome(ChromeConfig(title = "Products", showTopBar = true))
    }
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
                    RefreshableContainer(
                        isRefreshing = uiState.isLoading,
                        onRefresh = { productListViewModel.loadProducts(uiState.category) }
                    ) {
                        ProductGrid(
                            products = uiState.products,
                            onProductClick = { product -> onProductClick(product.id) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    //ProductGrid(products = uiState.products, onProductClick = {p -> onProductClick(p.id)})

                }
            }
        }


    }

}

@Composable
fun CategoryChips(
    categories: List<String>,
    selected: String?,
    onCategorySelected: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selected == null,
            onClick = { onCategorySelected(null) },
            label = { Text("All", style = MaterialTheme.typography.bodyMedium) }
        )

        categories.forEach { category ->
            FilterChip(
                selected = selected == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category, style = MaterialTheme.typography.bodyMedium) }
            )
        }
    }
}