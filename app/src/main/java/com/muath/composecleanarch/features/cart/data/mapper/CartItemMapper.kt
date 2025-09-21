package com.muath.composecleanarch.features.cart.data.mapper

import com.muath.composecleanarch.features.cart.data.local.entity.CartItemEntity
import com.muath.composecleanarch.features.cart.domain.entity.CartItem

fun CartItemEntity.toDomain(): CartItem{
    return CartItem(
        productId = this.productId,
        quantity = this.quantity

    )
}


fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        productId = this.productId,
        quantity = this.quantity
    )
}
