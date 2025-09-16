package com.muath.composecleanarch.features.productlist.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.muath.composecleanarch.core.model.Product


@Composable
fun ProductItemCard(
    product: Product,
    onClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(product) }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f) // take remaining space
            ) {
                product.name?.let { Text(it, style = MaterialTheme.typography.titleMedium) }
                Spacer(Modifier.height(4.dp))
                Text(product.category ?: "", style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(8.dp))
                Text("${product.price} $", style = MaterialTheme.typography.titleSmall)
            }
//
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(start = 8.dp),
                contentScale = ContentScale.Crop
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ProductItemCardPreview() {
    ProductItemCard(
        product = Product(
            id = 1,
            name = "Sample Product",
            category = "Electronics",
            price = 299.99,
            imageUrl = "android.resource://com.muath.composecleanarch/drawable/ic_launcher_background",
            description = "This is a sample product description."
        ),
        onClick = {}
    )
}