package com.muath.composecleanarch.features.productlist.data.datasource

import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.features.productlist.data.local.dao.ProductDao
import com.muath.composecleanarch.features.productlist.data.local.dao.ProductFavoriteDao
import com.muath.composecleanarch.features.productlist.data.local.entity.ProductFavoriteEntity
import com.muath.composecleanarch.features.productlist.data.mapper.toDomain
import com.muath.composecleanarch.features.productlist.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProductLocalDataSource {
    suspend fun getProduct(id: Int): Product?
    suspend fun saveProducts(products: List<Product>)
    suspend fun isFavorite(productId: Int): Boolean
    suspend fun getFavoriteProducts(): List<Product>
    suspend fun toggleFavorite(productId: Int, isFavorite: Boolean)
    fun observeFavoriteIds(): Flow<List<Int>>
}

class ProductLocalDataSourceImp @Inject constructor(
    private val dao: ProductDao,
    private val favoriteDao: ProductFavoriteDao
)
    : ProductLocalDataSource{
    override suspend fun getProduct(id: Int): Product? {
        return dao.getProductById(id)?.toDomain()
    }

    override suspend fun saveProducts(products: List<Product>) {
        dao.insertProducts(products.map { it.toEntity()})
    }

    override suspend fun isFavorite(productId: Int): Boolean {
        return favoriteDao.isFavorite(productId)
    }

    override suspend fun getFavoriteProducts(): List<Product> {
        return favoriteDao.getFavoriteProducts().map { it.toDomain() }
    }

    override suspend fun toggleFavorite(productId: Int, isFavorite: Boolean) {
        when(isFavorite){
            true -> favoriteDao.addToFavorite(ProductFavoriteEntity(productId))
            false -> favoriteDao.removeFromFavorite(ProductFavoriteEntity(productId))
        }
    }

    override fun observeFavoriteIds(): Flow<List<Int>> {
        return favoriteDao.observeFavoriteIds()
    }


}