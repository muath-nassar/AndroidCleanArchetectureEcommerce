package com.muath.composecleanarch.features.cart.domain.repo

import com.muath.composecleanarch.features.cart.domain.entity.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addUpdateCartItem(cartItem: CartItem)
    fun observeCartItem(itemId: Int): Flow<CartItem?>
    fun observeCart(): Flow<List<CartItem>>
    suspend fun emptyCart(): Unit
}