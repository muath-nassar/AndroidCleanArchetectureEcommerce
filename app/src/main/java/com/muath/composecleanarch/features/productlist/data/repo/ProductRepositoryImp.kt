package com.muath.composecleanarch.features.productlist.data.repo

import com.muath.composecleanarch.core.common.AppError
import com.muath.composecleanarch.core.common.Result
import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.core.network.ConnectivityObserver
import com.muath.composecleanarch.features.productlist.data.datasource.ProductLocalDataSource
import com.muath.composecleanarch.features.productlist.data.datasource.ProductRemoteDataSource
import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImp @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource,
    private val connectivityObserver: ConnectivityObserver
)
    : ProductRepository {
    override fun getProducts(
        category: String?,
        limit: Int?,
        sort: ProductRepository.Sort?
    ): Flow<Result<List<Product>>> {
        return flow {
            // Loading
            if (!connectivityObserver.isConnected()){
                emit(Result.Err(AppError.Network.NoConnection))
                return@flow // stop flow
            }
            emit(Result.Loading)
            // Get data from repo
            val result = remoteDataSource.getProducts(category,limit,sort)
            emit(result)
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        val tryLocal = localDataSource.getProduct(id)
        if (tryLocal != null){
            return Result.Ok(tryLocal)
        }
        // Try remote if not cached
        if (!connectivityObserver.isConnected())
            return Result.Err(AppError.Network.NoConnection)

        val result = remoteDataSource.getProductById(id)
        val data = result.getOrNull()
        if (data != null){
            localDataSource.saveProducts(listOf(data))
        }
        return result
    }
}