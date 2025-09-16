package com.muath.composecleanarch.features.productlist.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muath.composecleanarch.core.ui.root.ChromeConfig


@Composable
fun ProductListScreen(
    onProductClick: (Int) -> Unit,
    showSnackBar: (String, String?) -> Unit,
    applyChrome: (ChromeConfig) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        applyChrome(ChromeConfig(title = "Products"))
    }
    Button(onClick = { showSnackBar("Hello", null) }) {
        Text("show snack")
    }

}

@Composable @Preview
fun ProductListScreenPreview() {
    ProductListScreen(
        onProductClick = {},
        showSnackBar = { _, _ -> },
        applyChrome = {}
    )
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

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun CategoryChipsPreview() {
    val categories = listOf("Category 1", "Category 2", "Category 3")
    CategoryChips(categories = categories, selected = "Category 3", onCategorySelected = {})
}
