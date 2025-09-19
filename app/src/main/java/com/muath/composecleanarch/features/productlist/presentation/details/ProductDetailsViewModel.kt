package com.muath.composecleanarch.features.productlist.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muath.composecleanarch.features.productlist.domain.usecase.GetProductByIdUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.muath.composecleanarch.core.common.Result
import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val useCase: GetProductByIdUseCase,
    private val repository: ProductRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState

    fun loadProduct(productId: Int){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = useCase(productId)
            when(result){
                is Result.Err -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.errorOrNull()
                        )
                }
                is Result.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                is Result.Ok<Product> -> {
                    _uiState.value = _uiState.value.copy(
                        product = result.getOrNull(),
                        isLoading = false,
                        error = null
                    )

                }
            }
        }
    }

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