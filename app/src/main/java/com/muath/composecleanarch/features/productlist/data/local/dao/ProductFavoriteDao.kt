package com.muath.composecleanarch.features.productlist.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muath.composecleanarch.features.productlist.data.local.entity.ProductFavoriteEntity
import com.muath.composecleanarch.features.productlist.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductFavoriteDao {
    @Query("""
        SELECT p.* FROM products p
        INNER JOIN favorite_products f ON p.id = f.id
    """)
    suspend fun getFavoriteProducts(): List<ProductEntity>

    @Query("SELECT EXISTS(SELECT * FROM favorite_products WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(favoriteProductEntity: ProductFavoriteEntity)

    @Delete
    suspend fun removeFromFavorite(favoriteProductEntity: ProductFavoriteEntity)

    @Query("SELECT id FROM favorite_products")
    fun observeFavoriteIds(): Flow<List<Int>>

}