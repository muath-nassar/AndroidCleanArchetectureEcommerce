package com.muath.composecleanarch.features.productlist.data.datasource

import com.muath.composecleanarch.core.common.AppError
import com.muath.composecleanarch.core.common.Result
import com.muath.composecleanarch.core.common.toAppError
import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.features.productlist.data.mapper.toDomain
import com.muath.composecleanarch.features.productlist.data.remote.api.ProductApiService
import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import javax.inject.Inject

interface ProductRemoteDataSource {
    suspend fun getProducts(
        category: String? = null,
        limit: Int? = null,
        sort: ProductRepository.Sort? = null
    ): Result<List<Product>>

    suspend fun getProductById(id: Int): Result<Product>
}

class ProductRemoteDataSourceImp @Inject constructor(
    private val api: ProductApiService
) : ProductRemoteDataSource {

    override suspend fun getProducts(
        category: String?,
        limit: Int?,
        sort: ProductRepository.Sort?
    ): Result<List<Product>> {
        return try {
            val sortStr = when (sort) {
                ProductRepository.Sort.ASC -> "asc"
                ProductRepository.Sort.DESC -> "desc"
                null -> null
            }

            val dtos = if (category == null) {
                api.getProducts(limit, sortStr)
            } else {
                api.getProductsByCategory(category, limit, sortStr)
            }

            Result.Ok(dtos.map { it.toDomain() })
        } catch (e: Exception) {
            Result.Err(e.toAppError())
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        return try {
            val dto = api.getProductById(id)
            if (dto == null) {
                Result.Err(AppError.Network.ClientError("Product not found"))
            } else {
                Result.Ok(dto.toDomain())
            }
        } catch (e: Exception) {
            Result.Err(e.toAppError())
        }
    }
}
