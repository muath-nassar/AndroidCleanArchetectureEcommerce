package com.muath.composecleanarch.features.cart.domain.usecase

import com.muath.composecleanarch.features.cart.domain.entity.CartItem
import com.muath.composecleanarch.features.cart.domain.repo.CartRepository
import javax.inject.Inject

class UpdateCartItemUSeCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartItem: CartItem) {
        cartRepository.addUpdateCartItem(cartItem)
    }

}