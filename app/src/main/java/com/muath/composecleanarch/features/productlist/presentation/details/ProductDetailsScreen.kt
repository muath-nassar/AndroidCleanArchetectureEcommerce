package com.muath.composecleanarch.features.productlist.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.core.ui.root.ChromeConfig
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.muath.composecleanarch.R
import com.muath.composecleanarch.core.ui.components.AppBottomSheetDialog
import com.muath.composecleanarch.core.ui.components.ProductFavoriteButton
import com.muath.composecleanarch.features.cart.presentation.components.CartItemControlView
import com.muath.composecleanarch.features.cart.presentation.components.CartItemViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    showSnackBar: (String, String?) -> Unit,
    applyChrome: (ChromeConfig) -> Unit,
    viewModel: ProductDetailsViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var chromeConfig: ChromeConfig = ChromeConfig(
        title = uiState.product?.name ?: "Product Details",
        showTopBar = true,
        showBottomBar = false,
        navIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Back") }
        }
    )

    LaunchedEffect(Unit){
        applyChrome(chromeConfig)
        viewModel.loadProduct(productId)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        when{
            uiState.isLoading ->{
            }
            uiState.error != null ->{
            }
            uiState.product != null ->{
                chromeConfig = chromeConfig.copy(title = uiState.product?.name ?: "")
                applyChrome(chromeConfig)
                ProductDetailsContent(
                    viewModel = viewModel,
                    product = uiState.product!!,
                    onFavoriteClick = { viewModel.toggleFavorite(uiState.product!!.id) },
                    onAddToCart = { showSnackBar("Coming soon", null) }
                )
            }
        }
    }
}

@Composable
fun ProductDetailsContent(
    viewModel: ProductDetailsViewModel,
    cartItemViewModel: CartItemViewModel = hiltViewModel(),
    product: Product,
    onFavoriteClick: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    val favoriteIds by viewModel.favoriteIds.collectAsState()
    var buyProduct by remember { mutableStateOf<Product?>(null) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp) // large header image
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.description,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            ProductFavoriteButton(
                isFavorite = product.id in favoriteIds,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Product Name
        Text(
            text = product.name ?: "",
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Price
        Text(
            text = "$${product.price}",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description (scrollable if long)
        Text(
            text = product.description?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.weight(1f)) // push button to bottom

        // Add to Cart button
        Button(
            onClick = {
                onAddToCart.invoke()
                buyProduct = product
                      },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Add to Cart", style = MaterialTheme.typography.labelLarge)
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
    }
}

