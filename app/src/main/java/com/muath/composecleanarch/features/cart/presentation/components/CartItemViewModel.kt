package com.muath.composecleanarch.features.cart.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muath.composecleanarch.features.cart.domain.entity.CartItem
import com.muath.composecleanarch.features.cart.domain.usecase.SubscribeToCartItemUseCase
import com.muath.composecleanarch.features.cart.domain.usecase.UpdateCartItemUSeCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@HiltViewModel
class CartItemViewModel @Inject constructor(
    private val subscribeUseCase: SubscribeToCartItemUseCase,
    private val updateCartItemUseCase: UpdateCartItemUSeCase,
) : ViewModel() {

    fun subscribeToCartItem(productId: Int): Flow<CartItem?> {
        return subscribeUseCase(productId)
    }

    fun updateCartItem(cartItem: CartItem){
        viewModelScope.launch {
            updateCartItemUseCase(cartItem)
        }
    }



    fun addToCartBtnPressed(productId: Int) {
        viewModelScope.launch {
            // check if already exists
            val currentItem = subscribeUseCase(productId)  // Flow<CartItem?>
                .firstOrNull() // get current snapshot once
            if (currentItem == null) {
                updateCartItemUseCase(CartItem(productId, 1))
            }
        }
    }
}