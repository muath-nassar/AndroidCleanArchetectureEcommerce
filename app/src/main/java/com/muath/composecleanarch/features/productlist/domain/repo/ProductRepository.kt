package com.muath.composecleanarch.features.productlist.domain.repo

import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.core.common.Result
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    enum class Sort{
        ASC,
        DESC
    }
    fun getProducts(
        category: String? = null,
        limit: Int? = null,
        sort: Sort? = null
    ): Flow<Result<List<Product>>>

    suspend fun getProductById(id: Int): Result<Product>

    suspend fun toggleFavorite(productId: Int)

    fun observeFavoriteIds(): Flow<List<Int>>
}