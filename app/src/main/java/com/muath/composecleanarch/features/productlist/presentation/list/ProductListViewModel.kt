package com.muath.composecleanarch.features.productlist.presentation.list

import androidx.lifecycle.viewModelScope
import com.muath.composecleanarch.core.common.Result
import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import com.muath.composecleanarch.features.productlist.domain.usecase.GetProductsUseCase
import com.muath.composecleanarch.features.productlist.presentation.ProductBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductsUseCase,
    repository: ProductRepository
): ProductBaseViewModel(repository){
    companion object{
        private val BASIC_CATEGORIES = listOf("electronics", "jewelery",
            "men's clothing", "women's clothing")
    }


    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState
    init {
        _uiState.value = _uiState.value.copy(categoryList = BASIC_CATEGORIES)
        loadProducts()
    }
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
                        error = result.errorOrNull(),
                        category = category
                    )
                }
                is Result.Loading -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        error = null,
                        category = category
                    )
                }
                is Result.Ok<List<Product>> -> {
                    _uiState.value = _uiState.value.copy(
                        products = result.getOrNull() ?: emptyList(),
                        isLoading = false,
                        error = null,
                        category = category
                    )
                }
            }
            }
        }
    }

}