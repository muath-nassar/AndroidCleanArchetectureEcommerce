package com.muath.composecleanarch.features.cart.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muath.composecleanarch.features.cart.domain.entity.CartProduct
import com.muath.composecleanarch.features.cart.domain.repo.CartRepository
import com.muath.composecleanarch.features.cart.domain.usecase.EmptyCartUseCase
import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val emptyCartUseCase: EmptyCartUseCase,
) : ViewModel() {

    private val _cartProducts = MutableStateFlow<List<CartProduct>>(emptyList())
    val cartProducts = _cartProducts.asStateFlow()

    init {
        loadCart()
    }

    private fun loadCart() {
        viewModelScope.launch {
            cartRepository.observeCart().collect { cartItems ->
                val newList = mutableListOf<CartProduct>()

                for (cartItem in cartItems) {
                    val productResult = productRepository.getProductById(cartItem.productId)
                    val product = productResult.getOrNull()
                    if (product != null) {
                        newList.add(CartProduct(product, cartItem.quantity))
                    }
                }

                _cartProducts.value = newList
            }
        }
    }
    fun clearCart() {
        viewModelScope.launch {
            emptyCartUseCase.invoke()
        }
    }
}
