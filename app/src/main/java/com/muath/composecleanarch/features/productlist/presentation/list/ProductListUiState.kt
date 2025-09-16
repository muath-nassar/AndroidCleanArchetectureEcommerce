package com.muath.composecleanarch.features.productlist.presentation.list

import com.muath.composecleanarch.core.common.AppError
import com.muath.composecleanarch.core.model.Product

data class ProductListUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: AppError? = null,
    val category: String? = null,
    val categoryList: List<String> = emptyList()
)