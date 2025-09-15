package com.muath.composecleanarch.features.productlist.domain.usecase

import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import kotlinx.coroutines.flow.Flow
import com.muath.composecleanarch.core.common.Result

import javax.inject.Inject


class GetProductsUseCase @Inject constructor(val repository: ProductRepository){
     operator fun invoke(
        category: String? = null,
        limit: Int? = null,
        sort: ProductRepository.Sort? = null
    ): Flow<Result<List<Product>>>{
     return repository.getProducts(category, limit, sort)
    }
}