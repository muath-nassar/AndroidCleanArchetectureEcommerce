package com.muath.composecleanarch.features.cart.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart_items",
    indices = [Index(value = ["productId"])]
)
data class CartItemEntity(
    @PrimaryKey val productId: Int,
    val quantity: Int
)
