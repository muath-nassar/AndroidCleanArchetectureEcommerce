package com.muath.composecleanarch.features.productlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class ProductBaseViewModel(
    private val repository: ProductRepository): ViewModel(){
    val favoriteIds: StateFlow<List<Int>> =
        repository.observeFavoriteIds().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun toggleFavorite(productId: Int) {
        viewModelScope.launch {
            repository.toggleFavorite(productId)
        }
    }
}