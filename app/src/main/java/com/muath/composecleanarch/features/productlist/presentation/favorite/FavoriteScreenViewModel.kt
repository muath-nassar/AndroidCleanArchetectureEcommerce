package com.muath.composecleanarch.features.productlist.presentation.favorite

import androidx.lifecycle.viewModelScope
import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import com.muath.composecleanarch.features.productlist.domain.usecase.GetProductByIdUseCase
import com.muath.composecleanarch.features.productlist.presentation.ProductBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    repository: ProductRepository,
    private val getProductById: GetProductByIdUseCase
): ProductBaseViewModel(repository){
    val favoriteProducts: StateFlow<List<Product>> =
        favoriteIds.map { favoriteIds ->
            favoriteIds.mapNotNull { id ->
                val result = getProductById(id)
                result.getOrNull()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}