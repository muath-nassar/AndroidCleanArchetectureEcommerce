package com.muath.composecleanarch.features.productlist.data.remote.api

import com.muath.composecleanarch.features.productlist.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int? = null,
        @Query("sort") sort: String? = null
    ): List<ProductDto>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String,
        @Query("limit") limit: Int? = null,
        @Query("sort") sort: String? = null
        ): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): ProductDto?

}