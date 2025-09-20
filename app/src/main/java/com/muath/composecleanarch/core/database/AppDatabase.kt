package com.muath.composecleanarch.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muath.composecleanarch.features.cart.data.local.dao.CartDao
import com.muath.composecleanarch.features.cart.data.local.entity.CartItemEntity
import com.muath.composecleanarch.features.productlist.data.local.dao.ProductDao
import com.muath.composecleanarch.features.productlist.data.local.dao.ProductFavoriteDao
import com.muath.composecleanarch.features.productlist.data.local.entity.ProductEntity
import com.muath.composecleanarch.features.productlist.data.local.entity.ProductFavoriteEntity

/**
 * Skeleton Room database.
 * Features will contribute their Entities + DAOs here later.
 */
@Database(
    entities = [
        ProductEntity::class,
        ProductFavoriteEntity::class,
        CartItemEntity::class,
               ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // Future examples:
    abstract fun productDao(): ProductDao
    abstract fun productFavoriteDao(): ProductFavoriteDao
    abstract fun cartDao(): CartDao
}
