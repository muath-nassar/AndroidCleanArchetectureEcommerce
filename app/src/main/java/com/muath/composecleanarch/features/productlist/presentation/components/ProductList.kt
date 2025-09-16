package com.muath.composecleanarch.features.productlist.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.muath.composecleanarch.core.model.Product

@Composable
fun ProductList(
    products: List<Product>,
    onItemClick: (Product) -> Unit,
    modifier: Modifier
) {
    LazyColumn (modifier=modifier){
        items(
            products,
            key={ it.id },
            itemContent = {p ->
                ProductItemCard(p, onClick = onItemClick)
            }
            )
    }
}