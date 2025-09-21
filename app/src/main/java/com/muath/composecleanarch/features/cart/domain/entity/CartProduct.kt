package com.muath.composecleanarch.features.cart.domain.entity

import com.muath.composecleanarch.core.model.Product

data class CartProduct(
    val product: Product,
    val quantity: Int
)