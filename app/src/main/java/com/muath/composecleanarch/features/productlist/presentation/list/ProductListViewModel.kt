package com.muath.composecleanarch.features.productlist.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muath.composecleanarch.core.common.Result
import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import com.muath.composecleanarch.features.productlist.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductsUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState

    fun loadProducts(
        category: String? = null,
        limit: Int? = null,
        sort: ProductRepository.Sort? = null
        ){
        viewModelScope.launch {
            getProductListUseCase(category, limit, sort).collectLatest {
             result ->   when(result){
                is Result.Err -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.errorOrNull()
                    )
                }
                is Result.Loading -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        error = null,
                    )
                }
                is Result.Ok<List<Product>> -> {
                    _uiState.value = _uiState.value.copy(
                        products = result.getOrNull() ?: emptyList(),
                        isLoading = false,
                        error = null,
                    )
                }
            }
            }
        }
    }
}