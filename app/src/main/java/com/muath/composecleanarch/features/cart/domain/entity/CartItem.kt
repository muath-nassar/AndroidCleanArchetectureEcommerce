package com.muath.composecleanarch.features.cart.domain.entity

import com.muath.composecleanarch.core.model.Product

data class CartItem(
    val productId: Int,
    val quantity: Int
)