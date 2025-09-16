package com.muath.composecleanarch.features.productlist.domain.usecase

import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import kotlinx.coroutines.flow.Flow
import com.muath.composecleanarch.core.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject


class GetProductsUseCase @Inject constructor(
    val repository: ProductRepository,
    ){
     suspend operator fun invoke(
        category: String? = null,
        limit: Int? = null,
        sort: ProductRepository.Sort? = null
    ): Flow<Result<List<Product>>>{

         val result = withContext(Dispatchers.IO){
             repository.getProducts(category, limit, sort)
         }
     return result
    }
}