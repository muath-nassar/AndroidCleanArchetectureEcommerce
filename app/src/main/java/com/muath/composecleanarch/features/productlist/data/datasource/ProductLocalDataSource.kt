package com.muath.composecleanarch.features.productlist.data.datasource

import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.features.productlist.data.local.dao.ProductDao
import com.muath.composecleanarch.features.productlist.data.mapper.toDomain
import com.muath.composecleanarch.features.productlist.data.mapper.toEntity
import javax.inject.Inject

interface ProductLocalDataSource {
    suspend fun getProduct(id: Int): Product?
    suspend fun saveProducts(products: List<Product>)
}

class ProductLocalDataSourceImp @Inject constructor(
    private val dao: ProductDao
)
    : ProductLocalDataSource{
    override suspend fun getProduct(id: Int): Product? {
        return dao.getProductById(id)?.toDomain()
    }

    override suspend fun saveProducts(products: List<Product>) {
        dao.insertProducts(products.map { it.toEntity()})
    }

}