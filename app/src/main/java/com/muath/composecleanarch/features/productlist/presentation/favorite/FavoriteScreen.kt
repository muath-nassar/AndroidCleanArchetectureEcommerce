package com.muath.composecleanarch.features.productlist.presentation.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.muath.composecleanarch.R
import com.muath.composecleanarch.core.ui.root.ChromeConfig
import com.muath.composecleanarch.features.productlist.presentation.components.ProductGrid

@Composable
fun FavoriteScreen(
    viewModel: FavoriteListViewModel = hiltViewModel(),
    showSnackBar: (String, String?) -> Unit,
    applyChrome: (ChromeConfig) -> Unit,
    onProductClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val title = stringResource(R.string.favorite)
    LaunchedEffect(Unit){
        applyChrome(ChromeConfig(title = title, showTopBar = true))
    }
    val favoriteProducts by viewModel.favoriteProducts.collectAsState()

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
                    text = "No favorites yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            ProductGrid(
                products = favoriteProducts,
                onProductClick = { product -> onProductClick(product.id) },
                viewModel = viewModel,
            )
        }
    }
}
