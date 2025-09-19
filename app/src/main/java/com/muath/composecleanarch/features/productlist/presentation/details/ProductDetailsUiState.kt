package com.muath.composecleanarch.features.productlist.presentation.details

import com.muath.composecleanarch.core.common.AppError
import com.muath.composecleanarch.core.model.Product

data class ProductDetailsUiState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: AppError? = null,
    val isFavorite: Boolean = false,
)