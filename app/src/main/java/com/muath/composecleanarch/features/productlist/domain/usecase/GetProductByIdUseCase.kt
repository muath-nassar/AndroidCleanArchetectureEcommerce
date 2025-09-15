package com.muath.composecleanarch.features.productlist.domain.usecase

import com.muath.composecleanarch.core.model.Product
import com.muath.composecleanarch.core.common.Result
import com.muath.composecleanarch.features.productlist.domain.repo.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(val repository: ProductRepository) {
    suspend operator fun invoke(id: Int) : Result<Product> {
        return repository.getProductById(id)
    }
}