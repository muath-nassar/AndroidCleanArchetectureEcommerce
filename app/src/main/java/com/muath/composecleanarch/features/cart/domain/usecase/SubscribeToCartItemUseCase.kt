package com.muath.composecleanarch.features.cart.domain.usecase

import com.muath.composecleanarch.features.cart.domain.repo.CartRepository
import javax.inject.Inject

class SubscribeToCartItemUseCase @Inject constructor(
    private val localRepository: CartRepository
){
    operator fun invoke(productId: Int) = localRepository.observeCartItem(productId)
}