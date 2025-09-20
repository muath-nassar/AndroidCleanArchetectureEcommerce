package com.muath.composecleanarch.features.cart.domain.repo

import com.muath.composecleanarch.features.cart.domain.entity.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addUpdateCartItem(cartItem: CartItem)
    suspend fun removeFromCart(productId: Int)
    fun observeCartItem(itemId: Int): Flow<CartItem?>
    suspend fun emptyCart(): Unit
    suspend fun existInCart(productId: Int): Boolean

}