package com.muath.composecleanarch.features.productlist.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.core.ui.components.ProductFavoriteButton
import com.muath.composecleanarch.features.productlist.presentation.list.ProductListViewModel

@Composable
fun ProductGrid(
    products: List<Product>,
    modifier: Modifier = Modifier,
    onProductClick:(Product) -> Unit,
    viewModel: ProductListViewModel

) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(6.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.fillMaxSize()

        ) {
        items(products){p ->
            ProductGridItem(
                p, onProductClick, viewModel = viewModel,
                onFavoriteClick = {viewModel.toggleFavorite(p.id)}
            )
        }
    }
}


@Composable
fun ProductGridItem(
    product: Product,
    onProductClick: (Product) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel,
    onFavoriteClick: ()->Unit
    ){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        onClick = { onProductClick(product)}
    ){
        val favoriteIds by viewModel.favoriteIds.collectAsState()
        Column(modifier = Modifier.padding(8.dp)) {
            Box(){
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.description,
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(1.2f)
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

            Spacer(modifier = Modifier.height(8.dp))
            product.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }


            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}