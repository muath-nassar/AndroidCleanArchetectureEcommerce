package com.muath.composecleanarch.features.cart.domain.usecase

import com.muath.composecleanarch.features.cart.domain.repo.CartRepository
import javax.inject.Inject

class EmptyCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun invoke() {
        cartRepository.emptyCart()
    }
}