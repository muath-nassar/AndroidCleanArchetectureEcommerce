package com.muath.composecleanarch.features.productlist.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class ProductFavoriteEntity(
@PrimaryKey val id: Int)